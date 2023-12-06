package day5

import kotlin.streams.asSequence


class Task {
    fun solve(input: List<String>): Long {
        // We've passed in a list of size==1 for the sake of not changing the ITask signature
        val almanac = input[0].split("\n\n")

        // Oh my lord rewrite this
        val seedRanges: List<LongRange> = Regex("""^seeds: ([\d|\s]+)""")
            .find(almanac[0])!!.groups[1]!!.value
            .split(" ")
            .chunked(2)
            .map { it[0].toLong()..<it[0].toLong() + it[1].toLong() }
            .toList()

        val graph = buildGraph(almanac.drop(1))

        val lowestSeeds = mutableMapOf<LongRange, Long>()
        seedRanges.parallelStream()
            .forEach { range ->
                lowestSeeds[range] = Long.MAX_VALUE

                range.asSequence().forEach {
                    val locationValue = graph.traverseFromSeedToLocation(it)

                    if (locationValue < lowestSeeds[range]!!) {
                        lowestSeeds[range] = locationValue
                    }
                }
            }

        return lowestSeeds.values.min()
    }

    private fun buildGraph(mappings: List<String>): AdjacencyList {
        val graph = AdjacencyList()

        // TODO: We could automate this, if the sections ever grow/vary
        graph.createVertex("seed", "soil")
        graph.createVertex("soil", "fertilizer")
        graph.createVertex("fertilizer", "water")
        graph.createVertex("water", "light")
        graph.createVertex("light", "temperature")
        graph.createVertex("temperature", "humidity")
        graph.createVertex("humidity", "location")
        graph.createVertex("location", "[end]") // ew

        mappings.map { mapString ->

            // Build graph from maps
            val components = mapString.split("\n")
            val (source, destination) = Regex("""^(\w+)-to-(\w+) map:""").find(components[0])!!.destructured
            val sourceVertex = graph.getVertexBySource(source)
            val destinationVertex = graph.getVertexBySource(destination)


            components.drop(1).filter { it.isNotBlank() }.map {
                val (destinationRangeStartString, sourceRangeStartString, rangeLengthString) =
                    Regex("""^(\d+) (\d+) (\d+)$""").find(it)!!.destructured

                val range = Range(
                    sourceRangeStartString.toLong(),
                    destinationRangeStartString.toLong(),
                    rangeLengthString.toLong()
                )

                graph.addEdge(sourceVertex, destinationVertex, range)
            }
        }

        return graph
    }
}

data class Range(
    val sourceStart: Long,
    val destinationStart: Long,
    val rangeLength: Long
) {
    fun contains(stepValue: Long): Boolean = sourceStart <= stepValue && stepValue < sourceStart + rangeLength
    fun convert(stepValue: Long): Long = destinationStart + (stepValue - sourceStart)
}

data class Edge(
    val source: Vertex,
    val destination: Vertex,
    val range: Range,
)

data class Vertex(
    val sourceCategory: String, // e.g 'seed'
    val destinationCategory: String, // e.g 'soil'
)

class AdjacencyList {
    private val adjacencyMap = mutableMapOf<Vertex, MutableList<Edge>>()

    // Bit lazy, but this lets us create the vertices without holding them all at once
    private val vertexLookUp = mutableMapOf<String, Vertex>()

    fun createVertex(sourceCategory: String, destinationCategory: String): Vertex {
        val vertex = Vertex(sourceCategory, destinationCategory)
        adjacencyMap[vertex] = mutableListOf()
        vertexLookUp[sourceCategory] = vertex
        return vertex
    }

    fun addEdge(source: Vertex, destination: Vertex, range: Range) {
        val edge = Edge(source, destination, range)
        adjacencyMap[source]?.add(edge)
    }

    fun getVertexBySource(sourceCategory: String): Vertex {
        return vertexLookUp[sourceCategory]!!
    }

    fun traverseFromSeedToLocation(seed: Long): Long {
        var stepValue = seed
        var currentVertex: Vertex = getVertexBySource("seed")

        while (currentVertex.destinationCategory != "[end]") {
            // To save on memory, we've only added explicit changes to the x->x pattern
            val edges = adjacencyMap[currentVertex]!!

            // If we don't find the value, it stays the same
            stepValue = edges.find { it.range.contains(stepValue) }?.range?.convert(stepValue) ?: stepValue
            currentVertex = getVertexBySource(currentVertex.destinationCategory)
        }

        // currentVertex is now the "location" vertex, so stepValue is the location map we walked to get here
        return stepValue
    }
}
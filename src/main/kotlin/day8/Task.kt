package day8

import kotlin.math.max

class Task {
    fun solve(input: List<String>): Long {
        val network = Network()

        val stepsToFollow = input[0]
        val mapContents = input.drop(1)
            .map { val (node, left, right) = """^(\w+) = \((\w+), (\w+)\)$""".toRegex().find(it)!!.destructured
                Node(node, left, right)
            }
            .forEach {
                network.addNode(it)
            }


        // Walk all paths until they loop; find multiplicative factor until they do
        val nodeTravellers: List<NodeTraveller> = network.getStartingNodes()
            .map { NodeTraveller(network, stepsToFollow, it) }

        nodeTravellers.parallelStream().forEach { it.walkUntilAnExit() }

        val numberOfStepsForNodesToGetToEnd = nodeTravellers.map { it.numberOfStepsInPath }.toList()
        // 14935034899483
        return findLCMOfListOfNumbers(numberOfStepsForNodesToGetToEnd)
    }

    private fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
    }

    private fun findLCM(a: Long, b: Long): Long {
        val larger = max(a, b)
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0.toLong() && lcm % b == 0.toLong()) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}

class NodeTraveller(val network: Network, private val stepsToFollow: String, startingNode: Node) {
    private var currentNode: Node = startingNode

    var numberOfStepsInPath = 0L

    fun walkUntilAnExit() {
        var stepIndex = 0

        while (!isOnFinishNode() || numberOfStepsInPath == 0L) {
            numberOfStepsInPath += 1
            val nextNodeId = if (stepsToFollow[stepIndex] == 'L') currentNode.leftId else currentNode.rightId
            stepIndex = (stepIndex + 1) % stepsToFollow.length
            currentNode = network.getNodeById(nextNodeId)
        }
    }

    private fun isOnFinishNode() = currentNode.id.endsWith("Z")
}

typealias NodeId = String
data class Node(val id: NodeId, var leftId: NodeId, val rightId: NodeId)

class Network {
    private val nodes = mutableMapOf<NodeId, Node>()

    fun addNode(node: Node) {
        nodes[node.id] = node
    }

    fun getNodeById(startingNodeId: String): Node = nodes[startingNodeId]!!
    fun getStartingNodes(): List<Node> = nodes.entries.filter { it.key.endsWith("A") }.map { it.value }.toList()
}
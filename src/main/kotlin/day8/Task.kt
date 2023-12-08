package day8

import ITask

class Task: ITask {
    override fun solve(input: List<String>): Int {
        val network = Network()

        val stepsToFollow = input[0]
        val mapContents = input.drop(1)
            .map { val (node, left, right) = """^(\w+) = \((\w+), (\w+)\)$""".toRegex().find(it)!!.destructured
                Node(node, left, right)
            }
            .forEach {
                network.addNode(it)
            }

        val startingNodeId = "AAA"
        var totalSteps = 0
        var stepIndex = 0

        var currentNode = network.getNodeById(startingNodeId)
        while (currentNode.id != "ZZZ") {
            totalSteps += 1

            val nextNodeId = if (stepsToFollow[stepIndex] == 'L') currentNode.leftId else currentNode.rightId
            stepIndex = (stepIndex + 1) % stepsToFollow.length // TODO: Verify this works properly

            currentNode = network.getNodeById(nextNodeId)
        }


        return totalSteps
    }
}

typealias NodeId = String
data class Node(val id: NodeId, var leftId: NodeId, val rightId: NodeId)

class Network {
    private val nodes = mutableMapOf<NodeId, Node>()

    fun addNode(node: Node) {
        nodes[node.id] = node
    }

    fun getNodeById(startingNodeId: String): Node = nodes[startingNodeId]!!
}
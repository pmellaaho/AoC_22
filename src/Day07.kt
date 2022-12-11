class Node(val id: String, private val size: Int = 0) {
    var parent: Node? = null
    var children: MutableList<Node> = mutableListOf()

    fun addNode(node: Node) {
        children.add(node)
    }

    private val isFile get() = size > 0
    private val isDirectory get() = size == 0

    fun subdirectories(): List<Node> {
        val dirs: MutableList<Node> = mutableListOf()
        children.forEach {
            if (it.isDirectory) {
                dirs.add(it)
                dirs.addAll(it.subdirectories())
            }
        }
        return dirs
    }

    fun getContainedSize(): Int {
        return children.map {
            if (it.isFile) it.size else it.getContainedSize()
        }.sumOf { it }
    }

    fun findRoot(): Node {
        var root = this
        while (root.parent != null) {
            root = root.parent!!
        }
        return root
    }

    override fun toString(): String {
        var s = id
        if (children.isNotEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }

    companion object {
        fun fromString(input: String): Node {
            val name = input.substringAfter(" ")
            val size = input.substringBefore(" ").toInt()
            return Node(name, size)
        }
    }
}

fun String.isCdRootCmd() = contains("cd /")
fun String.isDir() = contains("dir ")
fun String.isFile() = first().isDigit()
fun String.isPreviousDirCmd() = contains("cd ..")
fun String.isCdCmd() = contains(" cd ")

fun main() {

    fun initDirTree(input: List<String>): Node {
        var currentDir = Node("/")
        input.forEach {
            when {
                it.isCdRootCmd() -> {}
                it.isDir() -> {
                    val node = Node(it.substringAfter(" "))
                    node.parent = currentDir
                    currentDir.addNode(node)
                }
                it.isFile() -> {
                    val node = Node.fromString(it)
                    node.parent = currentDir
                    currentDir.addNode(node)
                }
                it.isPreviousDirCmd() -> {
                    currentDir = currentDir.parent ?: currentDir
                }
                it.isCdCmd() -> {
                    currentDir = currentDir.children
                        .first { child -> child.id == it.substringAfter("cd ") }
                }
                else -> {}
            }
        }
        return currentDir.findRoot()
    }

    /**
     * find all of the directories with a total size of at most 100000,
     * then calculate the sum of their total sizes.
     */
    fun part1(input: List<String>): Int {
        val dir = initDirTree(input)

        return dir.subdirectories().filter {
            it.getContainedSize() <= 100000
        }.sumOf { it.getContainedSize() }
    }

    /**
     * Find the smallest directory that, if deleted, would free up enough (at least 8381165)
     * space on the filesystem to run the update.
     */
    fun part2(input: List<String>): Int {
        val minSpaceToFree = 8381165
        val dir = initDirTree(input)

        return dir.subdirectories().filter {
            it.getContainedSize() >= minSpaceToFree
        }.minByOrNull { it.getContainedSize() }!!.getContainedSize()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day07_test")
//    val res = part2(testInput)
//    check(res == 24933642)  // test passes

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))  // wrong answer: 8389675
}


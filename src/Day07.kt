sealed class Node(val id: String) {
    var parent: Folder? = null
    var children: MutableList<Node> = mutableListOf()

    fun addNode(node: Node) {
        children.add(node)
    }

    fun findRoot(): Node {
        var root = this
        while (root.parent != null) {
            root = root.parent!!
        }
        return root
    }

    abstract fun getContainedSize(): Int

    override fun toString(): String {
        var s = id
        if (children.isNotEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}

class File(id: String, private val size: Int = 0) : Node(id) {
    override fun getContainedSize() = size

    companion object {
        fun fromString(input: String): File {
            val name = input.substringAfter(" ")
            val size = input.substringBefore(" ").toInt()
            return File(name, size)
        }
    }
}

class Folder(id: String) : Node(id) {

    fun subdirectories(): List<Folder> {
        val dirs: MutableList<Folder> = mutableListOf()
        children.forEach {
            if (it is Folder) {
                dirs.add(it)
                dirs.addAll(it.subdirectories())
            }
        }
        return dirs
    }

    override fun getContainedSize(): Int {
        return children.sumOf { it.getContainedSize() }
    }
}

fun String.isCdRootCmd() = contains("cd /")
fun String.isDir() = contains("dir ")
fun String.isFile() = first().isDigit()
fun String.isPreviousDirCmd() = contains("cd ..")
fun String.isCdCmd() = contains(" cd ")

fun main() {

    fun initDirTree(input: List<String>): Node {
        var currentDir = Folder("/")
        input.forEach {
            when {
                it.isCdRootCmd() -> {}
                it.isDir() -> {
                    val node = Folder(it.substringAfter(" "))
                    node.parent = currentDir
                    currentDir.addNode(node)
                }
                it.isFile() -> {
                    val node = File.fromString(it)
                    node.parent = currentDir
                    currentDir.addNode(node)
                }
                it.isPreviousDirCmd() -> {
                    currentDir = currentDir.parent ?: currentDir
                }
                it.isCdCmd() -> {
                    currentDir = currentDir.children
                        .first { child -> child.id == it.substringAfter("cd ") } as Folder
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
        val root = initDirTree(input) as Folder

        return root.subdirectories().filter {
            it.getContainedSize() <= 1_000_00
        }.sumOf { it.getContainedSize() }
    }

    /**
     * Find the smallest directory that, if deleted, would free up enough (at least 8381165)
     * space on the filesystem to run the update.
     */
    fun part2(input: List<String>): Int {
        val root = initDirTree(input) as Folder
        val totalSpace = 70_000_000
        val usedSpace = root.getContainedSize()
        val freeSpace = totalSpace - usedSpace
        val neededSpace = 30_000_000
        val minSpaceToFree = neededSpace - freeSpace

        return root.subdirectories().filter {
            it.getContainedSize() >= minSpaceToFree
        }.minByOrNull { it.getContainedSize() }!!.getContainedSize()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day07_test")
//    val res1 = part1(testInput)
//    check(res1 == 95_437)
//    val res2 = part2(testInput)
//    check(res2 == 24_933_642)  // test passes

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}


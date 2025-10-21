package org.example

class BackMeUpProcessFactory(val rawArgs: Array<String>) {

    fun createProcess(): BackMeUpProcess? {

        if (containsNoOrMoreThanOne(rawArgs.toList(), TOP_ARGS)) {
            println("Must contain exact one of these top arguments:\n\t-init\nt\t-diff\n\t-restore!")
            return null
        }

        val topArg = rawArgs.firstOrNull { it.startsWith(ARG_MARKER) }
            ?.removePrefix(ARG_MARKER)

        val remainingArgs = rawArgs.toMutableList()
        remainingArgs.remove(ARG_MARKER + topArg)  // removes the first occurrence if it exists

        return when (topArg) {
            ARG_INIT -> checkInitArgsAndCreateProcess(remainingArgs)
            ARG_DIFF -> checkDiffArgsAndCreateProcess(remainingArgs)
            ARG_RESTORE -> checkRestoreArgsAndCreateProcess(remainingArgs)
            else -> null
        }
    }

    private fun containsNoOrMoreThanOne(argsToCheck: List<String>, allowedArgs: Array<String>): Boolean {
        val count = argsToCheck.count { it in allowedArgs }
        return count != 1
    }

    private fun containsNotAtLeastOneOfEach(argsToCheck: List<String>, mustBeArgs: Array<String>): Boolean {
        return mustBeArgs.any { it !in argsToCheck }
    }

    private fun checkInitArgsAndCreateProcess(givenArgs: List<String>): BackMeUpProcess? {
        if (containsNotAtLeastOneOfEach(givenArgs, INIT_ARGS)) {
            return null
        }
        return BackMeUpInitializer(givenArgs.toList())
    }

    private fun checkDiffArgsAndCreateProcess(givenArgs: List<String>): BackMeUpProcess? {
        if (containsNotAtLeastOneOfEach(givenArgs, arrayOf(argOf(ARG_SRC)))) {
            return null
        }
        return BackMeUpDifferentiator(givenArgs.toList())
    }

    private fun checkRestoreArgsAndCreateProcess(givenArgs: List<String>): BackMeUpProcess? {
        if (containsNotAtLeastOneOfEach(givenArgs, RESTORE_ARGS)) {
            return null
        }
        return BackMeUpRestorer(givenArgs.toList())
    }

    /*
    Arguments hierarchy:

    -init       (creates first full backup)
    |___-save       (directory to be backed up)
    |___-storage    (directory to store the backup)
    -diff       (creates diff from specified backup)
    |___-src        (directory to compare)
    |___-storage    (optional, directory to store the diff backup)
    -restore    (restores complete file system from a backup)
    |___-from       (last state of backup to restore from)
    |___-to         (directory in which to restore)

    Note: Only one top level argument per execution allowed
 */
    companion object {
        const val ARG_MARKER = "-"
        const val ARG_INIT = "init"
        const val ARG_DIFF = "diff"
        const val ARG_RESTORE = "restore"
        const val ARG_SAVE = "save"
        const val ARG_STORAGE = "storage"
        const val ARG_SRC = "src"
        const val ARG_FROM = "from"
        const val ARG_TO = "to"

        val TOP_ARGS = arrayOf(argOf(ARG_INIT), argOf(ARG_DIFF), argOf(ARG_RESTORE))
        val INIT_ARGS = arrayOf(argOf(ARG_SAVE), argOf(ARG_STORAGE))
        val DIFF_ARGS = arrayOf(argOf(ARG_SRC), argOf(ARG_STORAGE))
        val RESTORE_ARGS = arrayOf(argOf(ARG_FROM), argOf(ARG_TO))

        private fun argOf(arg: String): String {
            return ARG_MARKER + arg
        }
    }
}
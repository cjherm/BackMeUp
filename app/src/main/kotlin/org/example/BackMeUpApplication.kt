package org.example

class BackMeUpApplication {

    fun start(args: Array<String>): BackMeUpResult {
        val process = generateProcessFromRawArgs(args)
        if (process == null) {
            println("ERROR!")
            return BackMeUpResult.ERROR
        }
        return process.start()
    }

    private fun generateProcessFromRawArgs(args: Array<String>): BackMeUpProcess? {

        val firstTopArg = args.firstOrNull { it.startsWith(ARG_MARKER) }
            ?.removePrefix(ARG_MARKER)

        val remainingArgs = args.toMutableList()
        remainingArgs.remove(ARG_MARKER + firstTopArg)  // removes the first occurrence if it exists

        return when (firstTopArg) {
            ARG_INIT -> BackMeUpInitializer(remainingArgs)
            else -> null
        }
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
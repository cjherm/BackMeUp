package org.example

import java.io.File

class BackMeUpProcessFactory(val rawArgs: Array<String>) {

    fun createProcess(): BackMeUpProcess? {

        if (containsNoOrMoreThanOneTopArg(rawArgs.toList())) {
            println("Must contain exact ONE of these top arguments:\n\t$ARG_INIT\n\t$ARG_DIFF\n\t$ARG_RESTORE!")
            return null
        }

        val topArg = rawArgs.first { it in TOP_ARGS }
        val remainingArgs = rawArgs.filter { it != topArg }

        return when (topArg) {
            ARG_INIT -> checkInitArgsAndCreateProcess(remainingArgs)
            ARG_DIFF -> checkDiffArgsAndCreateProcess(remainingArgs)
            ARG_RESTORE -> checkRestoreArgsAndCreateProcess(remainingArgs)
            else -> null
        }
    }

    private fun containsNoOrMoreThanOneTopArg(argsToCheck: List<String>): Boolean {
        val count = argsToCheck.count { it in TOP_ARGS }
        return count != 1
    }

    private fun retrieveArgValuesFromArgs(argKey: String, argsToCheck: List<String>): List<String> {
        val argValues = mutableListOf<String>()
        argsToCheck.forEachIndexed { index, value ->
            if (value == argKey && argsToCheck.size >= index + 2) {
                argValues.add(argsToCheck[index + 1])
            }
        }
        return argValues
    }

    private fun checkIfValuesAreValidDirectories(valuesList: List<String>, shouldBeEmpty: Boolean): Boolean {
        valuesList.forEach { value ->

            if (value.isEmpty()) {
                return false
            }

            val dirToCheck = File(value)

            // TODO Adapt use case: Should create dir if not existing?
            if (!dirToCheck.exists() || !dirToCheck.isDirectory) {
                return false
            }

            val isEmpty = dirToCheck.listFiles()?.isEmpty() ?: true

            if (isEmpty != shouldBeEmpty) {
                return false
            }
        }
        return true
    }

    private fun checkInitArgsAndCreateProcess(givenArgs: List<String>): BackMeUpProcess? {
        val saveValuesList = retrieveArgValuesFromArgs(ARG_SAVE, givenArgs)

        if (saveValuesList.isEmpty()) {
            println("No values for \"$ARG_SAVE\" found in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(saveValuesList, false)) {
            println("Values for \"$ARG_SAVE\" are not valid and/or empty directories!\n\t$saveValuesList")
            return null
        }

        val storageValuesList = retrieveArgValuesFromArgs(ARG_STORAGE, givenArgs)

        if (storageValuesList.isEmpty()) {
            println("No values for \"$ARG_STORAGE\" found in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(storageValuesList, true)) {
            println("Values for \"$ARG_STORAGE\" are not valid and/or non-empty directories!\n\t$storageValuesList")
            return null
        }

        // TODO clear duplicates

        return BackMeUpInitializer(InitProcessConfig())
    }

    private fun checkDiffArgsAndCreateProcess(givenArgs: List<String>): BackMeUpProcess? {
        val srcValuesList = retrieveArgValuesFromArgs(ARG_SRC, givenArgs)

        if (srcValuesList.isEmpty()) {
            println("No values for \"$ARG_SRC\" found in $givenArgs!")
            return null
        }

        if (srcValuesList.size > 1) {
            println("More than one value found for \"$ARG_SRC\" in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(srcValuesList, false)) {
            println("Values for \"$ARG_SRC\" are not valid and/or empty directories!\n\t$srcValuesList")
            return null
        }

        val storageValuesList = retrieveArgValuesFromArgs(ARG_STORAGE, givenArgs)

        // Explicit storage paths are not necessary
        if (storageValuesList.isNotEmpty()) {
            if (!checkIfValuesAreValidDirectories(storageValuesList, true)) {
                println("Values for \"$ARG_STORAGE\" are not valid and/or non-empty directories!\n\t$storageValuesList")
                return null
            }
        }

        // TODO clear duplicates

        return BackMeUpDifferentiator(DiffProcessConfig())
    }

    private fun checkRestoreArgsAndCreateProcess(givenArgs: List<String>): BackMeUpProcess? {
        val fromValuesList = retrieveArgValuesFromArgs(ARG_FROM, givenArgs)

        if (fromValuesList.isEmpty()) {
            println("No values for \"$ARG_FROM\" found in $givenArgs!")
            return null
        }

        if (fromValuesList.size > 1) {
            println("More than one value found for \"$ARG_FROM\" in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(fromValuesList, false)) {
            println("Values for \"$ARG_FROM\" are not valid and/or empty directories!\n\t$fromValuesList")
            return null
        }

        val toValuesList = retrieveArgValuesFromArgs(ARG_TO, givenArgs)

        if (toValuesList.isEmpty()) {
            println("No values for \"$ARG_TO\" found in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(toValuesList, true)) {
            println("Values for \"$ARG_TO\" are not valid and/or non-empty directories!\n\t$toValuesList")
            return null
        }

        // TODO clear duplicates

        return BackMeUpRestorer(RestoreProcessConfig())
    }

    /*
    Arguments hierarchy:

    -init       (creates first full backup)
    |___-save       (multiple directories to be backed up)
    |___-storage    (multiple directories to store the backup)
    -diff       (creates diff from specified backup)
    |___-src        (single directory to compare to)
    |___-storage    (optional, multiple directories to store the diff backup)
    -restore    (restores complete file system from a backup)
    |___-from       (single last state of backup to restore from)
    |___-to         (multiple directories in which to restore)

    Note: Only one top level argument per execution allowed
 */
    companion object {
        const val ARG_MARKER = "-"

        const val INIT = "init"
        const val SAVE = "save"
        const val STORAGE = "storage"

        const val DIFF = "diff"
        const val SRC = "src"

        const val RESTORE = "restore"
        const val FROM = "from"
        const val TO = "to"

        val ARG_INIT = argOf(INIT)
        val ARG_SAVE = argOf(SAVE)
        val ARG_STORAGE = argOf(STORAGE)
        val ARG_DIFF = argOf(DIFF)
        val ARG_SRC = argOf(SRC)
        val ARG_RESTORE = argOf(RESTORE)
        val ARG_FROM = argOf(FROM)
        val ARG_TO = argOf(TO)

        val TOP_ARGS = arrayOf(ARG_INIT, ARG_DIFF, ARG_RESTORE)

        private fun argOf(arg: String): String {
            return ARG_MARKER + arg
        }
    }
}
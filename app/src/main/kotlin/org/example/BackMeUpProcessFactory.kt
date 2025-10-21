package org.example

import java.io.File

class BackMeUpProcessFactory(val rawArgs: Array<String>) {

    fun createProcess(): BackMeUpProcess? {

        if (containsNoOrMoreThanOneTopArg(rawArgs.toList())) {
            println("Must contain exact ONE of these top arguments:\n\t-init\n\t-diff\n\t-restore!")
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
        val saveValuesList = retrieveArgValuesFromArgs(argOf(ARG_SAVE), givenArgs)

        if (saveValuesList.isEmpty()) {
            println("No values for \"-save\" found in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(saveValuesList, false)) {
            println("Values for \"-save\" are not valid and/or empty directories!\n\t$saveValuesList")
            return null
        }

        val storageValuesList = retrieveArgValuesFromArgs(argOf(ARG_STORAGE), givenArgs)

        if (storageValuesList.isEmpty()) {
            println("No values for \"-storage\" found in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(storageValuesList, true)) {
            println("Values for \"-storage\" are not valid and/or non-empty directories!\n\t$storageValuesList")
            return null
        }

        return BackMeUpInitializer(InitProcessConfig())
    }

    private fun checkDiffArgsAndCreateProcess(givenArgs: List<String>): BackMeUpProcess? {
        val srcValuesList = retrieveArgValuesFromArgs(argOf(ARG_SRC), givenArgs)

        if (srcValuesList.isEmpty()) {
            println("No values for \"-src\" found in $givenArgs!")
            return null
        }

        if (srcValuesList.size > 1) {
            println("More than one value found for \"-src\" in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(srcValuesList, false)) {
            println("Values for \"-src\" are not valid and/or empty directories!\n\t$srcValuesList")
            return null
        }

        val storageValuesList = retrieveArgValuesFromArgs(argOf(ARG_STORAGE), givenArgs)

        // Explicit storage paths are not necessary
        if (storageValuesList.isNotEmpty()) {
            if (!checkIfValuesAreValidDirectories(storageValuesList, true)) {
                println("Values for \"-storage\" are not valid and/or non-empty directories!\n\t$storageValuesList")
                return null
            }
        }

        return BackMeUpDifferentiator(DiffProcessConfig())
    }

    private fun checkRestoreArgsAndCreateProcess(givenArgs: List<String>): BackMeUpProcess? {
        val fromValuesList = retrieveArgValuesFromArgs(argOf(ARG_FROM), givenArgs)

        if (fromValuesList.isEmpty()) {
            println("No values for \"-from\" found in $givenArgs!")
            return null
        }

        if (fromValuesList.size > 1) {
            println("More than one value found for \"-from\" in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(fromValuesList, false)) {
            println("Values for \"-from\" are not valid and/or empty directories!\n\t$fromValuesList")
            return null
        }

        val toValuesList = retrieveArgValuesFromArgs(argOf(ARG_TO), givenArgs)

        if (toValuesList.isEmpty()) {
            println("No values for \"-to\" found in $givenArgs!")
            return null
        }

        if (!checkIfValuesAreValidDirectories(toValuesList, true)) {
            println("Values for \"-to\" are not valid and/or non-empty directories!\n\t$toValuesList")
            return null
        }

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

        const val ARG_INIT = "init"
        const val ARG_DIFF = "diff"
        const val ARG_RESTORE = "restore"
        const val ARG_SAVE = "save"
        const val ARG_STORAGE = "storage"
        const val ARG_SRC = "src"
        const val ARG_FROM = "from"
        const val ARG_TO = "to"

        val TOP_ARGS = arrayOf(argOf(ARG_INIT), argOf(ARG_DIFF), argOf(ARG_RESTORE))

        private fun argOf(arg: String): String {
            return ARG_MARKER + arg
        }
    }
}
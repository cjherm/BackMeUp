package org.example

import org.example.BackMeUpProcess.BackMeUpTopArgument

class BackMeUpApplication {

    fun start(args: Array<String>): BackMeUpResult {
        val process = extractArgsFromArgsArray(args)
        if (process == null) {
            println("ERROR!")
            return BackMeUpResult.ERROR
        }
        return process.start()
    }

    private fun extractArgsFromArgsArray(args: Array<String>): BackMeUpProcess? {

        val firstTopArg = args.firstOrNull { it.startsWith(ARG_MARKER) }
            ?.removePrefix(ARG_MARKER)

        val remainingArgs = args.toMutableList()
        remainingArgs.remove(ARG_MARKER + firstTopArg)  // removes the first occurrence if it exists

        return when (firstTopArg) {
            ARG_INIT -> BackMeUpInitializer(remainingArgs)
            else -> null
        }
    }

    companion object {
        const val ARG_INIT = "init"
        const val ARG_MARKER = "-"
    }
}
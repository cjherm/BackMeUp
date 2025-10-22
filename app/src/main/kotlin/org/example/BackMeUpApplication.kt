package org.example

import org.example.processes.BackMeUpProcessFactory

class BackMeUpApplication {

    fun start(args: Array<String>): BackMeUpResult {

        val factory = BackMeUpProcessFactory(args)
        val process = factory.createProcess()

        if (process == null) {
            println("ERROR!")
            return BackMeUpResult.ERROR
        }

        return process.prepare()
    }
}
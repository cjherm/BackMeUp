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

        val result = process.prepare()

        return if (result == BackMeUpResult.SUCCESS) {
            process.start()
        } else {
            result
        }
    }
}
package org.example

import java.io.File

class BackMeUpInitializer(val args: List<String>) : BackMeUpProcess {

    override fun start(): BackMeUpResult {
        val file = File(args[0])
        return if (file.exists()) {
            BackMeUpResult.SUCCESS
        } else {
            BackMeUpResult.ERROR
        }
    }
}
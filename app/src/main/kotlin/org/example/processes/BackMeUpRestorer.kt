package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.RestoreProcessConfig

class BackMeUpRestorer(val config: RestoreProcessConfig) : BackMeUpProcess {

    // TODO implement this BackMeUpProcess

    override fun start(): BackMeUpResult {
        return BackMeUpResult.ERROR
    }
}
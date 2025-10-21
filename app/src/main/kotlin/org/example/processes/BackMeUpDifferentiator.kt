package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.DiffProcessConfig

class BackMeUpDifferentiator(val config: DiffProcessConfig) : BackMeUpProcess {

    // TODO implement this BackMeUpProcess

    override fun start(): BackMeUpResult {
        return BackMeUpResult.ERROR
    }
}
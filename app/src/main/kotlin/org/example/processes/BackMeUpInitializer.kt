package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.BackMeUpProcessConfig
import org.example.configs.InitProcessConfig

class BackMeUpInitializer(val config: InitProcessConfig) : BackMeUpProcess {

    // TODO implement this BackMeUpProcess

    override fun start(): BackMeUpResult {
        return BackMeUpResult.ERROR
    }

    override fun getConfig(): BackMeUpProcessConfig {
        return config
    }
}
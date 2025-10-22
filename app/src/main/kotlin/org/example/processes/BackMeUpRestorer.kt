package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.BackMeUpProcessConfig
import org.example.configs.RestoreProcessConfig

/**
 * The process that executes a full restoration of the desired backup state.
 */
class BackMeUpRestorer(val config: RestoreProcessConfig) : BackMeUpProcess {

    // TODO implement this BackMeUpProcess

    override fun start(): BackMeUpResult {
        return BackMeUpResult.ERROR
    }

    override fun getConfig(): BackMeUpProcessConfig {
        return config
    }
}
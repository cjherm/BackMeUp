package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.BackMeUpProcessConfig
import org.example.configs.InitProcessConfig

/**
 * The process that executes a full initial backup. Serves as the base for all future differential backups
 * that will be based on this single backup and all following differential backups.
 */
class BackMeUpInitializer(val config: InitProcessConfig) : BackMeUpProcess {

    // TODO implement this BackMeUpProcess

    override fun start(): BackMeUpResult {
        return BackMeUpResult.ERROR
    }

    override fun getConfig(): BackMeUpProcessConfig {
        return config
    }
}
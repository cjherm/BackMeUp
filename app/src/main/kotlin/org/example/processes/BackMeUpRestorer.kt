package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.RestoreProcessConfig

/**
 * The process that executes a full restoration of the desired backup state.
 */
class BackMeUpRestorer(private val config: RestoreProcessConfig) : BackMeUpProcess {

    // TODO implement this BackMeUpProcess

    override fun prepare(): BackMeUpResult {
        return BackMeUpResult.ERROR
    }
}
package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.DiffProcessConfig

/**
 * The process that executes a differential backup. It will compare the current state of the directories to be tracked
 * with the most current state of a possible restoration based on the initial and all following differential backups.
 * Will only back up changes (new and modified files) compared to the last time a differential backup was done.
 */
class BackMeUpDifferentiator(private val config: DiffProcessConfig) : BackMeUpProcess {

    // TODO implement this BackMeUpProcess

    override fun prepare(): BackMeUpResult {
        return BackMeUpResult.ERROR
    }

    override fun start(): BackMeUpResult {
        return BackMeUpResult.ERROR
    }
}
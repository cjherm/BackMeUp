package org.example.processes

import org.example.BackMeUpResult

interface BackMeUpProcess {

    enum class ProcessType {
        INIT, DIFF, RESTORE
    }

    /**
     * Prepares the process for the actual execution. Provides the possibility to check before the execution.
     */
    fun prepare(): BackMeUpResult
    fun start(): BackMeUpResult
}
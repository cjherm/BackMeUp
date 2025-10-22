package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.BackMeUpProcessConfig

interface BackMeUpProcess {

    enum class ProcessType {
        INIT, DIFF, RESTORE
    }

    fun start(): BackMeUpResult
    fun getConfig(): BackMeUpProcessConfig
}
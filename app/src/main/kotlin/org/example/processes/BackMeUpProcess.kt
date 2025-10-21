package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.BackMeUpProcessConfig

interface BackMeUpProcess {

    fun start(): BackMeUpResult

    fun getConfig(): BackMeUpProcessConfig
}
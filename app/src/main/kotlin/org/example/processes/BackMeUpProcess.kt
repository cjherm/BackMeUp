package org.example.processes

import org.example.BackMeUpResult

interface BackMeUpProcess {

    fun start(): BackMeUpResult
}
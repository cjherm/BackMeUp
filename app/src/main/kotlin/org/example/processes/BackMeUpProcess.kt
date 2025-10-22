package org.example.processes

import org.example.BackMeUpResult

interface BackMeUpProcess {

    enum class ProcessType {
        INIT, DIFF, RESTORE
    }

    fun prepare(): BackMeUpResult
}
package org.example

interface BackMeUpProcess {

    enum class BackMeUpTopArgument() {
        INVALID,
        INIT
    }

    fun start(): BackMeUpResult

    companion object {
        val INVALID = BackMeUpProcess
    }
}
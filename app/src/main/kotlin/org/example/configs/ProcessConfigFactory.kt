package org.example.configs

import org.example.processes.BackMeUpProcess.ProcessType

class ProcessConfigFactory(private val processType: ProcessType) {

    fun createConfig(): BackMeUpProcessConfig {

        return when (processType) {

            ProcessType.INIT -> {
                InitProcessConfig(saveValuesList, storageValuesList)
            }

            ProcessType.DIFF -> {
                DiffProcessConfig(srcValuesList, storageValuesList)
            }

            ProcessType.RESTORE -> {
                RestoreProcessConfig(fromValuesList, toValuesList)
            }
        }
    }

    lateinit var saveValuesList: List<String>
    lateinit var storageValuesList: List<String>
    lateinit var srcValuesList: List<String>
    lateinit var fromValuesList: List<String>
    lateinit var toValuesList: List<String>
}
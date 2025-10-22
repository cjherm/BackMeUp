package org.example.configs

import org.example.processes.BackMeUpProcess.ProcessType

// TODO Move some checks from process factory into this class to avoid double checks
class ProcessConfigFactory(private val processType: ProcessType) {

    var saveValuesList = emptyList<String>()
    var storageValuesList = emptyList<String>()
    var srcValuesList = emptyList<String>()
    var fromValuesList = emptyList<String>()
    var toValuesList = emptyList<String>()

    fun createConfig(): BackMeUpProcessConfig? {

        return when (processType) {

            ProcessType.INIT -> {
                tryToCreateInitProcessCfg()
            }

            ProcessType.DIFF -> {
                tryToCreateDiffProcessCfg()
            }

            ProcessType.RESTORE -> {
                tryToCreateRestoreProcessCfg()
            }
        }
    }

    private fun tryToCreateInitProcessCfg(): InitProcessConfig? {
        if (saveValuesList.isEmpty() || storageValuesList.isEmpty()) {
            return null
        }
        return InitProcessConfig(saveValuesList, storageValuesList)
    }

    private fun tryToCreateDiffProcessCfg(): DiffProcessConfig? {
        if (storageValuesList.isEmpty()) {
            storageValuesList = tryToFindStorage()
        }
        if (srcValuesList.isEmpty() || storageValuesList.isEmpty()) {
            return null
        }
        return DiffProcessConfig(srcValuesList, storageValuesList)
    }

    private fun tryToFindStorage(): List<String> {
        // TODO Try to determine storage based on known init/diffs
        return listOf("")
    }

    private fun tryToCreateRestoreProcessCfg(): RestoreProcessConfig? {
        if (fromValuesList.isEmpty() || toValuesList.isEmpty()) {
            return null
        }
        return RestoreProcessConfig(fromValuesList, toValuesList)
    }
}
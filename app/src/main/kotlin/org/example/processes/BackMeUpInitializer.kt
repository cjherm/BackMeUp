package org.example.processes

import org.example.BackMeUpResult
import org.example.configs.InitProcessConfig
import java.io.File

/**
 * The process that executes a full initial backup. Serves as the base for all future differential backups
 * that will be based on this single backup and all following differential backups.
 */
class BackMeUpInitializer(private val config: InitProcessConfig) : BackMeUpProcess {

    override fun prepare(): BackMeUpResult {
        calculateBackUpSize()
        checkAndSaveAvailableDiskSpace()
        return BackMeUpResult.ERROR
    }

    override fun start(): BackMeUpResult {
        return BackMeUpResult.ERROR
    }

    private fun calculateBackUpSize() {
        config.getListOfDirectoriesToBackUp().forEach { path ->
            countFilesAndSize(path)
        }
    }

    private fun checkAndSaveAvailableDiskSpace() {
        val listOfUnsuitableStorageDirs = mutableListOf<String>()
        config.getListOfDirectoriesToStoreBackUps().forEach { path ->
            val space = File(path).usableSpace
            config.availableSpaceList[path] = space
            if (space < config.totalFilesSizeToBeBackedUp) {
                listOfUnsuitableStorageDirs.add(path)
            }
        }
        config.listOfUnsuitableStorageDirs = listOfUnsuitableStorageDirs
    }

    private fun countFilesAndSize(dirPath: String) {
        val dir = File(dirPath)
        val allFiles = dir.walkTopDown().filter { it.isFile }.toList()

        val fileCount = allFiles.size
        config.totalFilesCountToBeBackedUp += fileCount

        val totalSize = allFiles.sumOf { it.length() }
        config.totalFilesSizeToBeBackedUp += totalSize
    }
}
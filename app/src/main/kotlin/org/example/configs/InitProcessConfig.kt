package org.example.configs

class InitProcessConfig(private val saveList: List<String>, private val storageList: List<String>) :
    BackMeUpProcessConfig {

    var totalFilesCountToBeBackedUp: Int = 0
    var totalFilesSizeToBeBackedUp: Long = 0
    var availableSpaceList = mutableMapOf<String, Long>()
    var listOfUnsuitableStorageDirs = mutableListOf<String>()

    fun getListOfDirectoriesToBackUp(): List<String> {
        return saveList
    }

    fun getListOfDirectoriesToStoreBackUps(): List<String> {
        return storageList
    }
}
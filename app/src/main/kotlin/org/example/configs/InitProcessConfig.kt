package org.example.configs

class InitProcessConfig(private val saveList: List<String>, private val storageList: List<String>) :
    BackMeUpProcessConfig {

    fun getListOfDirectoriesToBackUp(): List<String> {
        return saveList
    }

    fun getListOfDirectoriesToStoreBackUps(): List<String> {
        return storageList
    }
}
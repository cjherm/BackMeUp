package org.example.configs

class DiffProcessConfig(private val srcList: List<String>, private val storageList: List<String>) :
    BackMeUpProcessConfig {

    fun getListOfDirectoriesToDiffFrom(): List<String> {
        return srcList
    }

    fun getListOfDirectoriesToStoreBackUps(): List<String> {
        return storageList
    }
}
package org.example.configs

class RestoreProcessConfig(private val fromList: List<String>, private val toList: List<String>) :
    BackMeUpProcessConfig {

    fun getListOfDirectoriesToDiffFrom(): List<String> {
        return fromList
    }

    fun getListOfDirectoriesToStoreBackUps(): List<String> {
        return toList
    }
}
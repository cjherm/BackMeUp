package org.example.configs

interface BackUpInfo {
    // The ID of the BackUp to allow to use more than one backup
    val globalId: String

    // The absolute path to the first full backup. This is not absolute necessary
    // as we can find the initial backup through iterating back all differential backups.
    val initPath: String

    // The interval in between differential backups in days (24h)
    val backUpIntervals: String

    // List of all directories to track for backup
    val trackedDirs: List<String>

    // To make sure this file was not modified manually
    val checkSum: String
}
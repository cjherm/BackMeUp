package org.example.configs

data class BackUpInfo(
    // The ID of the BackUp to allow to use more than one backup
    val id: String,

    // The absolute path to the first full backup
    val initPath: String,

    // The absolute path to the last/most current differential backup
    val lastDiffPath: String,

    // The time stamp of the last/most current differential backup
    val lastDiffDate: String,

    // The interval in between differential backups in days (24h)
    val backUpIntervals: String,

    // List of all directories to back up
    val dirsToSave: List<String>
)
package org.example.configs

data class BackUpDiffInfo(

    override val globalId: String,

    override val initPath: String,

    // The absolute path to the last/most current differential backup
    val lastDiffPath: String,

    // The time stamp of the last/most current differential backup
    val lastDiffDate: String,

    override val backUpIntervals: String,

    override val trackedDirs: List<String>,

    override val checkSum: String

) : BackUpInfo
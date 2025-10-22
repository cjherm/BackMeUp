package org.example.configs

data class BackUpInitInfo(

    override val globalId: String,

    override val initPath: String,

    override val backUpIntervals: String,

    override val trackedDirs: List<String>,

    override val checkSum: String

) : BackUpInfo
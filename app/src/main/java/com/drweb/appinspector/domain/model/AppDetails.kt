package com.drweb.appinspector.domain.model

data class AppDetails(
    val appName: String,
    val versionName: String,
    val packageName: String,
    val checksum: String,
)
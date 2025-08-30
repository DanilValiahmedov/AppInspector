package com.drweb.appinspector.domain.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable,
)
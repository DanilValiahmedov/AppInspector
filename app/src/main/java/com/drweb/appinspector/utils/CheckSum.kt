package com.drweb.appinspector.utils

import java.io.File
import java.security.MessageDigest

object CheckSum {
    fun calculateChecksum(file: File): String {
        val digest = MessageDigest.getInstance("SHA-1")
        file.inputStream().use { fis ->
            val buffer = ByteArray(1024)
            var read: Int
            while (fis.read(buffer).also { read = it } != -1) {
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }
}
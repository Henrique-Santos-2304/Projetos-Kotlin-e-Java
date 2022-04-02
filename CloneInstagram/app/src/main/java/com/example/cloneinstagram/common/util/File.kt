package com.example.cloneinstagram.common.util

import android.app.Activity
import com.example.cloneinstagram.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object File {
    private const val FORMAT_DATE = "yyyy-MM-dd-HH-mm-ss-SSS"

    fun generatedFile(activity: Activity): File{
        val formatDate = SimpleDateFormat(FORMAT_DATE, Locale.US).format(System.currentTimeMillis())+ ".jpg"

        val mediaDirs = activity.externalMediaDirs.firstOrNull()?.let {
            File(it, activity.getString(R.string.app_name)).apply { mkdirs() }
        }
        val outputDirs = if(mediaDirs != null && mediaDirs.exists()) mediaDirs else activity.filesDir
        return File(outputDirs, formatDate)
    }
}
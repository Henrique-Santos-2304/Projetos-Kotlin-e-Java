package com.example.cloneinstagram.common.model

import android.net.Uri

data class Post(
    val uuid: String,
    val uri: Uri,
    val caption: String,
    val timestamp: Long,
    val userAuth: UserAuth
)

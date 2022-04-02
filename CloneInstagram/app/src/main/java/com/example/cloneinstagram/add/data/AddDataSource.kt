package com.example.cloneinstagram.add.data

import android.net.Uri
import com.example.cloneinstagram.common.base.RequestCallback
import com.example.cloneinstagram.common.model.UserAuth
import java.lang.UnsupportedOperationException

interface AddDataSource {
    fun createPost(userUuid: String, uri: Uri, caption: String,  callback: RequestCallback<Boolean>){
        throw UnsupportedOperationException()
    }
    fun fetchSession(): UserAuth {throw UnsupportedOperationException()}
}
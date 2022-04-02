package com.example.cloneinstagram.register.data

import android.net.Uri

class RegisterRepository(private val dataSource: RegisterDataSource) {

    fun create(email:String, callback: RegisterCallback){
        dataSource.create(email,callback)
    }
    fun create(email:String, name: String, password: String, callback: RegisterCallback){
        dataSource.create(email,name, password, callback)
    }

    fun updateUri(photoUri: Uri, callback: RegisterCallback){
        dataSource.updateUser(photoUri, callback)
    }
}
package com.example.cloneinstagram.login.data

import com.example.cloneinstagram.common.model.UserAuth

interface LoginCallback {
    fun onSucess(userAuth: UserAuth)
    fun onFailure(message: String)
    fun onComplete()
}
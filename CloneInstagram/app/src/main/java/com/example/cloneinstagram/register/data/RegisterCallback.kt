package com.example.cloneinstagram.register.data

import com.example.cloneinstagram.common.model.UserAuth

interface RegisterCallback {
    fun onSucess()
    fun onFailure(message: String)
    fun onComplete()
}
package com.example.cloneinstagram.login.data

interface LoginDataSource {
    fun login(email: String, password: String, callback: LoginCallback)
}
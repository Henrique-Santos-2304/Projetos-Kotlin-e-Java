package com.example.cloneinstagram.splash.data

interface SplashDataSource {
    fun session(callback: SplashCallback)
}
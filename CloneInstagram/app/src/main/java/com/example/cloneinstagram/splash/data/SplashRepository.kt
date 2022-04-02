package com.example.cloneinstagram.splash.data

class SplashRepository(
    val dataSouce: SplashDataSource
) {
    fun session(callback: SplashCallback){
        dataSouce.session(callback)
    }
}
package com.example.cloneinstagram.login.data

import android.os.Handler
import android.os.Looper
import com.example.cloneinstagram.common.model.DataBase


class FakeDataSource: LoginDataSource {
    override fun login(email: String, password: String, callback: LoginCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = DataBase.usersAuth.firstOrNull { email == it.email }

            when{
                userAuth == null -> callback.onFailure("Usuario não encontrado")
                userAuth.password != password -> callback.onFailure("Senha não confere")
                else -> {
                    DataBase.sessionAuth = userAuth
                    callback.onSucess(userAuth)
                }
            }

            callback.onComplete()

        },2000)
    }
}
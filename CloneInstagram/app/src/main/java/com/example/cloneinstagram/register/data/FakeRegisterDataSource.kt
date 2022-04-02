package com.example.cloneinstagram.register.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.example.cloneinstagram.common.model.DataBase
import com.example.cloneinstagram.common.model.Photo
import com.example.cloneinstagram.common.model.UserAuth
import java.util.*


class FakeRegisterDataSource: RegisterDataSource {
    override fun create(email: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            when (DataBase.usersAuth.firstOrNull { email == it.email }) {
                null -> callback.onSucess()
                else -> callback.onFailure("Usuario já cadastrado")
            }

            callback.onComplete()

        },2000)
    }

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            when (DataBase.usersAuth.firstOrNull { email == it.email }) {
                null -> createNewUser(email, name, password, callback)
                else -> callback.onFailure("Usuario já cadastrado")
            }

            callback.onComplete()

        },2000)
    }

    fun createNewUser(email: String, name: String, password: String, callback: RegisterCallback){

        val newUser = UserAuth(UUID.randomUUID().toString(), name, email, password)
        val created = DataBase.usersAuth.add(newUser)

        if(!created) {
            callback.onFailure("Erro ao cadastro novo Usúario no banco de dados")
        }else {
            DataBase.followers[newUser.uuid] = hashSetOf()
            DataBase.posts[newUser.uuid] = hashSetOf()
            DataBase.feeds[newUser.uuid] = hashSetOf()
            callback.onSucess()
        }
    }

    override fun updateUser(photoUri: Uri, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = DataBase.sessionAuth
           when(userAuth){
               null -> callback.onFailure("Usuario Não Encontrado")
               else -> updateNewPhoto(userAuth, photoUri, callback)
           }
            callback.onComplete()
        },2000)
    }

    private fun updateNewPhoto(
        userAuth: UserAuth,
        photoUri: Uri,
        callback: RegisterCallback
    ) {
        val newPhoto = Photo(userAuth.uuid, photoUri)
        val createPhoto = DataBase.photoUser.add(newPhoto)
        if (!createPhoto) callback.onFailure("Não foi possivel salvar a foto")
        else callback.onSucess()
    }

}
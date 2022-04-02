package com.example.cloneinstagram.register.presentation

import android.net.Uri
import com.example.cloneinstagram.register.RegisterPhoto
import com.example.cloneinstagram.register.data.RegisterCallback
import com.example.cloneinstagram.register.data.RegisterRepository

class RegisterPhotoPresenter(
    private var view : RegisterPhoto.View?,
    private val repository: RegisterRepository
): RegisterPhoto.Presenter {
    override fun update(photoUri: Uri) {
        view?.showProgress(true)

        repository.updateUri(photoUri, object : RegisterCallback{
            override fun onSucess() {
                view?.onUpdateSucess()
            }

            override fun onFailure(message: String) {
                view?.onUpdateFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }

        })
    }

    override fun onDestroy() {
        view =  null
    }
}
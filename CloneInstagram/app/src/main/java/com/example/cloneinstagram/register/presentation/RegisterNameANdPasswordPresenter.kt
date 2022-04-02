package com.example.cloneinstagram.register.presentation

import com.example.cloneinstagram.R
import com.example.cloneinstagram.register.RegisterNameAndPassword
import com.example.cloneinstagram.register.data.RegisterCallback
import com.example.cloneinstagram.register.data.RegisterRepository

class RegisterNameANdPasswordPresenter(
    private var view : RegisterNameAndPassword.View?,
    private val repository: RegisterRepository
    ): RegisterNameAndPassword.Presenter {

    override fun create(email: String, name: String, password: String, confirmPassword: String) {
        val isNameValid = name.length >= 3
        val isPasswordValid = password.length >=4
        val isConfirmValid = password == confirmPassword

        if(!isNameValid) view?.displayNameFailure(R.string.invalid_name)
        else  view?.displayNameFailure(null)

        if(!isPasswordValid) {
            view?.displayPasswordFailure(R.string.invalid_password)
        } else{
            if (!isConfirmValid) view?.displayPasswordFailure(R.string.password_not_equal)
            else view?.displayPasswordFailure(null)
        }
        if(isNameValid && isPasswordValid && isConfirmValid){
            repository.create(email, name, password, object : RegisterCallback {

                override fun onSucess() {
                    view?.onCreateSucess(name)
                }

                override fun onFailure(message: String) {
                    view?.onCreateFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }
                })
        }
    }

    override fun onDestroy() {
        view= null
    }
}
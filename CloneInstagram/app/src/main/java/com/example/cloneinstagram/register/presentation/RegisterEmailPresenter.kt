package com.example.cloneinstagram.register.presentation

import android.util.Patterns
import com.example.cloneinstagram.R
import com.example.cloneinstagram.register.RegisterEmail
import com.example.cloneinstagram.register.data.RegisterCallback
import com.example.cloneinstagram.register.data.RegisterRepository

class RegisterEmailPresenter(
    private var view : RegisterEmail.View?,
    private val repository: RegisterRepository
    ): RegisterEmail.Presenter {
    override fun create(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if(!isEmailValid) view?.displayEmailFailure(R.string.invalid_email)
        else  {
            view?.displayEmailFailure(null)
            repository.create(email, object: RegisterCallback{
                override fun onSucess() {
                    view?.goToNameAndPasswordRegister(email)
                }

                override fun onFailure(message: String) {
                    view?.onEmailFailure(message)
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
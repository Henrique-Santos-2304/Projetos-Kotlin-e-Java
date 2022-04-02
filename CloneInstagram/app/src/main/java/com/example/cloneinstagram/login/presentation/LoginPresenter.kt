package com.example.cloneinstagram.login.presentation

import android.util.Patterns
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.model.UserAuth
import com.example.cloneinstagram.login.data.LoginCallback
import com.example.cloneinstagram.login.data.LoginRepository
import com.example.cloneinstagram.login.Login

class LoginPresenter(
    private var view: Login.View?,
    private val repository: LoginRepository
                     ): Login.Presenter {
    override fun login(email: String, password: String) {
        val autenticated  = verifyEmailAndPasswordData(email, password)

        if (autenticated){
            view?.showProgress(true)
            setStatusWithLoginCallback(email, password)
        }

    }

    private fun setStatusWithLoginCallback(email: String, password: String) {
        repository.login(email, password, object : LoginCallback {
            override fun onSucess(userAuth: UserAuth) {
                view?.onUserAuthenticated()
            }

            override fun onFailure(message: String) {
                view?.onUserUnauthorized(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }

        })
    }

    private fun verifyEmailAndPasswordData(email: String, password: String): Boolean {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8

        if (!isEmailValid) view?.displayEmailFailure(R.string.invalid_email)
        else view?.displayEmailFailure(null)

        if (!isPasswordValid) view?.displayPasswordFailure(R.string.invalid_password)
        else view?.displayPasswordFailure(null)

       return isEmailValid && isPasswordValid
    }

    override fun onDestroy() {
        view = null
    }
}
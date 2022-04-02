package com.example.cloneinstagram.login

import androidx.annotation.StringRes
import com.example.cloneinstagram.common.base.BasePresenter
import com.example.cloneinstagram.common.base.BaseView

interface Login {

    interface Presenter: BasePresenter{
        fun login(email: String, password: String)
    }

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun displayPasswordFailure(@StringRes emailError: Int?)
        fun onUserUnauthorized(message: String)
        fun onUserAuthenticated()
    }
}
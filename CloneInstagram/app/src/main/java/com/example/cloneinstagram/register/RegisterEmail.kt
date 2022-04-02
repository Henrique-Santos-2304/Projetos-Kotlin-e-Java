package com.example.cloneinstagram.register

import androidx.annotation.StringRes
import com.example.cloneinstagram.common.base.BasePresenter
import com.example.cloneinstagram.common.base.BaseView

interface RegisterEmail {

    interface Presenter: BasePresenter{
        fun create(email: String)

    }

    interface View : BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes email: Int?)
        fun onEmailFailure(message: String)
        fun goToNameAndPasswordRegister(email: String)
    }
}
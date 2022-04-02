package com.example.cloneinstagram.register

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import com.example.cloneinstagram.common.base.BasePresenter
import com.example.cloneinstagram.common.base.BaseView

interface RegisterNameAndPassword {
    interface Presenter : BasePresenter {
        fun create(email: String, name: String, password: String, confirmPassword: String)

    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameFailure(@StringRes nameError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onCreateFailure(message: String)
        fun onCreateSucess(name: String)
    }
}
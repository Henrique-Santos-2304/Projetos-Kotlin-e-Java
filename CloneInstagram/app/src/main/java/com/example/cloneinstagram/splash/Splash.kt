package com.example.cloneinstagram.splash

import com.example.cloneinstagram.common.base.BasePresenter
import com.example.cloneinstagram.common.base.BaseView

interface Splash {
    interface Presenter: BasePresenter{
        fun authenticated()
    }

    interface View: BaseView<Presenter>{
        fun goToMainScreen()
        fun goToLoginScreen()
    }

}
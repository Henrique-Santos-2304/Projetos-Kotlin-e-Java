package com.example.cloneinstagram.splash.presentation

import com.example.cloneinstagram.splash.Splash
import com.example.cloneinstagram.splash.data.SplashCallback
import com.example.cloneinstagram.splash.data.SplashRepository

class SplashPresentation(
    private var view: Splash.View?,
    private val repository: SplashRepository
): Splash.Presenter {
    override fun authenticated() {
        repository.session(object: SplashCallback{
            override fun onSucess() {
                view?.goToMainScreen()
            }

            override fun onFaillure() {
                view?.goToLoginScreen()
            }

        })
    }

    override fun onDestroy() {
       view = null
    }
}
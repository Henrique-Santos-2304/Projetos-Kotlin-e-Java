package com.example.cloneinstagram.splash.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatActivity
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.base.DependencyInjector
import com.example.cloneinstagram.common.extensions.animationEnd
import com.example.cloneinstagram.login.view.LoginActivity
import com.example.cloneinstagram.main.view.MainActivity
import com.example.cloneinstagram.splash.Splash
import com.example.cloneinstagram.splash.presentation.SplashPresentation
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), Splash.View {
    override lateinit  var presenter: Splash.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val repository = DependencyInjector.splashInjector()
        presenter = SplashPresentation(this, repository)


        animateSplashFadeIn()
    }



    override fun goToMainScreen() {
        val intent = Intent(baseContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        animateSplashFadeOut(intent)
    }

    override fun goToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        animateSplashFadeOut(intent)
    }

    private fun animateSplashFadeOut(intent: Intent){
        splash_img.animate().apply {
            duration = 1400
            alpha(0.0F)
            startDelay = 1000
            start()
            setListener(animationEnd {
                startActivity(intent)
            })

        }
    }
    private fun animateSplashFadeIn() {
        splash_img.animate().apply {
            duration = 1400
            alpha(1.0F)
            start()
            setListener(animationEnd { presenter.authenticated() })
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }


}
package com.example.cloneinstagram.common.base

import com.example.cloneinstagram.login.data.FakeDataSource
import com.example.cloneinstagram.login.data.LoginRepository
import com.example.cloneinstagram.register.data.FakeRegisterDataSource
import com.example.cloneinstagram.register.data.RegisterRepository
import com.example.cloneinstagram.splash.data.FakeLocalDataSource
import com.example.cloneinstagram.splash.data.SplashRepository

object DependencyInjector {
    fun splashInjector(): SplashRepository{
        return SplashRepository(FakeLocalDataSource())
    }
    fun loginInjector(): LoginRepository{
        return LoginRepository(FakeDataSource())
    }
    fun registerEmailInjector(): RegisterRepository{
        return RegisterRepository(FakeRegisterDataSource())
    }

}
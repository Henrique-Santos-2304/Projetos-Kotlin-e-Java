package com.example.cloneinstagram.splash.data

import com.example.cloneinstagram.common.model.DataBase

class FakeLocalDataSource: SplashDataSource {
    override fun session(callback: SplashCallback) {
        if(DataBase.sessionAuth !=null) callback.onSucess()
        else callback.onFaillure()
    }
}
package com.example.cloneinstagram.register

import android.net.Uri
import com.example.cloneinstagram.common.base.BasePresenter
import com.example.cloneinstagram.common.base.BaseView

interface RegisterPhoto {
    interface Presenter : BasePresenter {
        fun update(photoUri: Uri)

    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun onUpdateFailure(message: String)
        fun onUpdateSucess()
    }
}
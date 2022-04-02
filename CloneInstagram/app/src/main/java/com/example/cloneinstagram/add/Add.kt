package com.example.cloneinstagram.add

import android.net.Uri
import com.example.cloneinstagram.common.base.BasePresenter
import com.example.cloneinstagram.common.base.BaseView
import java.net.URI
import java.util.*

interface Add {
    interface Presenter: BasePresenter{
        fun createPost(uri: Uri, caption: String)
    }
    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayRequestSucess()
        fun displayRequestFailure(message: String)
    }
}
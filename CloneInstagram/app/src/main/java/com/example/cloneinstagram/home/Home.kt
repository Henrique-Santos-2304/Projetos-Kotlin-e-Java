package com.example.cloneinstagram.home

import com.example.cloneinstagram.common.base.BasePresenter
import com.example.cloneinstagram.common.base.BaseView

interface Home {
    interface Presenter: BasePresenter{

    }
    interface View: BaseView<Presenter>{

    }
}
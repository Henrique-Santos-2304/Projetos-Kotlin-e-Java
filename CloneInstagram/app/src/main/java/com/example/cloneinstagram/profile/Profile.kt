package com.example.cloneinstagram.profile

import com.example.cloneinstagram.common.base.BasePresenter
import com.example.cloneinstagram.common.base.BaseView
import com.example.cloneinstagram.common.model.Post
import com.example.cloneinstagram.common.model.UserAuth

interface Profile {
    interface Presenter: BasePresenter{
        fun fetchUserProfile()
        fun fetchUserPosts()
    }

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(userAuth: UserAuth)
        fun requestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
    }
}
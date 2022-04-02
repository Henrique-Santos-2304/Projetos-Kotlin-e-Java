package com.example.cloneinstagram.add.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.example.cloneinstagram.common.base.RequestCallback
import com.example.cloneinstagram.common.model.DataBase
import com.example.cloneinstagram.common.model.Post

class AddFakeRemoteDataSource: AddDataSource {
    private lateinit var createNewPost: Post

    override fun createPost(
        userUuid: String,
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {
        super.createPost(userUuid, uri, caption, callback)
        Handler(Looper.getMainLooper()).postDelayed({
            updatePosts(userUuid, uri, caption)
            updateFeedOfFollowers(userUuid)

            updateFeedUserApp(userUuid)

            callback.onSucess(true)
        }, 1000)
    }

    private fun updateFeedOfFollowers(userUuid: String) {
        var followers = DataBase.followers[userUuid]
        if (followers == null) {
            followers = mutableSetOf()
            DataBase.followers[userUuid] = followers
        } else {
            for (follower in followers) {
                DataBase.feeds[follower]?.add(createNewPost)
            }
        }
    }

    private fun updateFeedUserApp(userUuid: String) {
        DataBase.feeds[userUuid]?.add(createNewPost)
    }

    private fun updatePosts(userUuid: String, uri: Uri, caption: String) {
        var posts = DataBase.posts[userUuid]
        createNewPost = Post(userUuid, uri, caption, System.currentTimeMillis(), DataBase.sessionAuth!!)

        if (posts == null) {
            posts = mutableSetOf()
            DataBase.posts[userUuid] = posts
        }
        posts.add(createNewPost)
    }
}
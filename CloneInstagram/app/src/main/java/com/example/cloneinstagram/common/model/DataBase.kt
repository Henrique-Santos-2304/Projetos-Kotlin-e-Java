package com.example.cloneinstagram.common.model

import java.util.*

object DataBase {
    val usersAuth = hashSetOf<UserAuth>()
    val photoUser = hashSetOf<Photo>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, Set<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val userA = UserAuth(UUID.randomUUID().toString(), "UserA", "userA@email.com", "12345678")
        val userB = UserAuth(UUID.randomUUID().toString(), "userB", "userB@email.com", "87654321")

        usersAuth.add(userA)
        usersAuth.add(userB)

        createDataNewUsers(userA)
        createDataNewUsers(userB)

        sessionAuth = usersAuth.first()
    }

    fun createDataNewUsers(user: UserAuth){
        followers[user.uuid] = hashSetOf()
        posts[user.uuid] = hashSetOf()
        feeds[user.uuid] = hashSetOf()
    }
}
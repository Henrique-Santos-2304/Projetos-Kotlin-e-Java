package com.example.cloneinstagram.profile.view

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.base.BaseFragment
import com.example.cloneinstagram.common.model.Post
import com.example.cloneinstagram.common.model.UserAuth
import com.example.cloneinstagram.profile.Profile
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: BaseFragment<Profile.Presenter>(R.layout.fragment_profile), Profile.View {

    override lateinit var presenter: Profile.Presenter
    private val adapter = ProfileAdapter()


    override fun setupViews() {
        list_profile_rv.layoutManager = GridLayoutManager(requireContext(), 3)
        list_profile_rv.adapter= adapter
    }

    override fun setupPresenter() {
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }

    override fun showProgress(enabled: Boolean) {
        profile_progress.visibility = if(enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(userAuth: UserAuth) {
        profile_text_publish_count.text = userAuth.postCount.toString()
        profile_text_following_count.text = userAuth.followingCount.toString()
        profile_text_follower_count.text =  userAuth.followersCount.toString()
        profile_username.text = userAuth.name
        profile_bio.text = "TODO"

        presenter.fetchUserPosts()
    }

    override fun requestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun displayEmptyPosts() {
        profile_empty_post.visibility = View.VISIBLE
        list_profile_rv.visibility = View.GONE
        Toast.makeText(requireContext(), "Não foi encontrado nenhum post", Toast.LENGTH_LONG).show()
    }

    override fun displayFullPosts(posts: List<Post>) {
        profile_empty_post.visibility = View.GONE
        list_profile_rv.visibility = View.VISIBLE
    }

}
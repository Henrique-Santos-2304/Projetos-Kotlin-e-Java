package com.example.cloneinstagram.home.view


import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.base.BaseFragment
import com.example.cloneinstagram.home.Home
import kotlinx.android.synthetic.main.fragment_home_posts.*

class HomeFragment: BaseFragment<Home.Presenter>(R.layout.fragment_home_posts) {
    override lateinit var presenter: Home.Presenter

    override fun setupViews() {
        rv_home_posts.layoutManager = LinearLayoutManager(requireContext())
        rv_home_posts.adapter= HomeAdapter()
    }

    override fun setupPresenter() {
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }



}
package com.example.cloneinstagram.add.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cloneinstagram.R
import com.example.cloneinstagram.camera.view.CameraFragment
import com.example.cloneinstagram.gallery.view.GalleryFragment

class AddViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    val tabs = arrayOf(R.string.photo, R.string.gallery)

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment =
        when(tabs[position]){
        R.string.photo -> CameraFragment()
        R.string.gallery -> GalleryFragment()
        else -> throw IllegalArgumentException("Fragmento não existe")
        }
}
package com.example.cloneinstagram.main.view

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cloneinstagram.R
import com.example.cloneinstagram.add.view.AddFragment
import com.example.cloneinstagram.camera.view.CameraFragment
import com.example.cloneinstagram.common.extensions.replaceFragment
import com.example.cloneinstagram.home.view.HomeFragment
import com.example.cloneinstagram.profile.view.ProfileFragment
import com.example.cloneinstagram.search.view.SearchFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var currentFragment: Fragment? = null
    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var addFragment: AddFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = HomeFragment()
        profileFragment = ProfileFragment()
        searchFragment = SearchFragment()
        addFragment = AddFragment()

        customizeStatusBarStyle()
        customizeStyleToolbar()
        menu_bottom_navigation_main.setOnNavigationItemSelectedListener(this)
        menu_bottom_navigation_main.selectedItemId = R.id.menu_home

    }

    private fun customizeStyleToolbar() {
        setSupportActionBar(toolbar_main_activity)

        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
            it.setDisplayHomeAsUpEnabled(true)
            it.title = ""
        }
    }

    private fun customizeStatusBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            with(window) {
                this.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
                this.statusBarColor = ContextCompat.getColor(context, R.color.gray)
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var scroolToolbarFlag = false
        when(item.itemId){
              R.id.menu_home ->  {
                  if(currentFragment == homeFragment) return false
                  currentFragment = homeFragment
              }
              R.id.menu_profile -> {
                  if(currentFragment == profileFragment) return false
                  currentFragment = profileFragment
                  scroolToolbarFlag = true
              }
              R.id.menu_search ->   {
                  if(currentFragment == searchFragment) return false
                  currentFragment = searchFragment
              }
              R.id.menu_add ->  {
                  if(currentFragment == addFragment) return false
                  currentFragment = addFragment
              }
        }

        currentFragment?.let { replaceFragment(R.id.main_fragment,it) }
        setScroolToolbarFlag(scroolToolbarFlag)

        return true
    }

    private fun setScroolToolbarFlag(enabled: Boolean){
        val params = toolbar_main_activity.layoutParams as AppBarLayout.LayoutParams
        val coordinator = main_appbar_content.layoutParams as CoordinatorLayout.LayoutParams

        if(enabled){
            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinator.behavior = AppBarLayout.Behavior()
        }else{
            params.scrollFlags = 0
            coordinator.behavior = null
        }
        main_appbar_content.layoutParams = coordinator
    }

}
package com.example.cloneinstagram.add.view

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.cloneinstagram.R
import com.example.cloneinstagram.add.Add
import com.example.cloneinstagram.common.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment: BaseFragment<Add.Presenter>(R.layout.fragment_add), Add.View {
    override lateinit var presenter: Add.Presenter

    override fun setupViews() {
        val adapter = AddViewPagerAdapter(requireActivity())
        viewpager.adapter = adapter

        syncTabLayoutAnViewPager(adapter)
        checkPermissionCamera()

    }

    private fun checkPermissionCamera() {
        if (allPermissionsGranted()) startCamera()
        else getPermission.launch(REQUEST_CAMERA)
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(requireContext(),REQUEST_CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun startCamera(){
        setFragmentResult("cameraKey", bundleOf("startCamera" to true))
    }

    private val getPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(allPermissionsGranted()) startCamera()
        else Toast.makeText(requireContext(), getString(R.string.acess_not_enabled_camera), Toast.LENGTH_LONG).show()
    }

    private fun syncTabLayoutAnViewPager(adapter: AddViewPagerAdapter) {
        if (add_tab != null && viewpager != null) {
            checkEventItemTab(adapter)
            createTabMediator(adapter)
        }
    }

    private fun createTabMediator(adapter: AddViewPagerAdapter) {
        TabLayoutMediator(add_tab, viewpager) { tab, position ->
            tab.text = getString(adapter.tabs[position])
        }.attach()
    }

    private fun checkEventItemTab(adapter: AddViewPagerAdapter) {
        add_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.text){
                    getString(adapter.tabs[0]) -> startCamera()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    override fun setupPresenter() {
    }

    companion object{
        private const val REQUEST_CAMERA = android.Manifest.permission.CAMERA

    }
}
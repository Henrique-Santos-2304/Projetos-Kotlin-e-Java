package com.example.cloneinstagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cloneinstagram.R
import kotlinx.android.synthetic.main.fragment_welcome_register.*


class RegisterWelcomeFragment : Fragment(R.layout.fragment_welcome_register) {
    private var fragmentAttachListenner: FragmentAttachListenner? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString(KEY_NAME) ?: throw IllegalArgumentException("Email Not found")

        register_txt_welcome.text =getString(R.string.welcome_to_instagram, name)
        btn_welcome_continuee.isEnabled = true
        btn_welcome_continuee.setOnClickListener {
            fragmentAttachListenner?.goToPhotoScreen()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenner){
            fragmentAttachListenner =  context
        }
    }

    companion object{
        const val KEY_NAME = "key_name"
    }

    override fun onDestroy() {
        fragmentAttachListenner = null
        super.onDestroy()
    }


}
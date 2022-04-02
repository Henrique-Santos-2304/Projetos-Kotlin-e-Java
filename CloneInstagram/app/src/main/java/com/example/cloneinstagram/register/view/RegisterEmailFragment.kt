package com.example.cloneinstagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.base.DependencyInjector
import com.example.cloneinstagram.common.util.OnTextWatcher
import com.example.cloneinstagram.register.RegisterEmail
import com.example.cloneinstagram.register.presentation.RegisterEmailPresenter
import kotlinx.android.synthetic.main.fragment_register_email.*

class RegisterEmailFragment: Fragment(R.layout.fragment_register_email), RegisterEmail.View {
    override lateinit var presenter: RegisterEmail.Presenter
    private var fragmentAttachListenner: FragmentAttachListenner? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RegisterEmailPresenter(this, DependencyInjector.registerEmailInjector()).also { presenter = it }

        register_edit_email.addTextChangedListener(watcher)
        register_edit_email.addTextChangedListener(OnTextWatcher{
            displayEmailFailure(null)
        })

        register_txt_login.setOnClickListener { activity?.finish() }
        register_button_next.setOnClickListener {
            presenter.create(register_edit_email.text.toString())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenner){
            fragmentAttachListenner =  context
        }
    }

    private val watcher = OnTextWatcher{
      register_button_next.isEnabled= it.isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        register_button_next.showProgessBar(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        register_input_email.error = emailError?.let{getString( it )}
    }

    override fun onEmailFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun goToNameAndPasswordRegister(email: String) {
        fragmentAttachListenner?.gotNameAndPasswordScreen(email)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        fragmentAttachListenner = null
        super.onDestroy()
    }

}
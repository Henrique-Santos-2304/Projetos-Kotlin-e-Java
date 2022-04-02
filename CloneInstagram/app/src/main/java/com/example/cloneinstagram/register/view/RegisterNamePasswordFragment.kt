package com.example.cloneinstagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.base.DependencyInjector
import com.example.cloneinstagram.common.util.OnTextWatcher
import com.example.cloneinstagram.register.RegisterNameAndPassword
import com.example.cloneinstagram.register.presentation.RegisterNameANdPasswordPresenter
import kotlinx.android.synthetic.main.fragment_register_name_password.*


class RegisterNamePasswordFragment : Fragment(R.layout.fragment_register_name_password), RegisterNameAndPassword.View {
    private var fragmentAttachListenner: FragmentAttachListenner? = null
    override lateinit var presenter: RegisterNameAndPassword.Presenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = DependencyInjector.registerEmailInjector()
        presenter = RegisterNameANdPasswordPresenter(this, repository)

        val email = arguments?.getString(KEY_EMAIL) ?: throw IllegalArgumentException("Email Not found")

        verifyEventTextInInputs()

        register_txt_login_goBack.setOnClickListener { activity?.finish() }
        register_np_button_next.setOnClickListener {
            presenter.create(
                email = email,
                name = input_edit_name_register_name.text.toString(),
                password = input_edit_password_register_name.text.toString(),
                confirmPassword = input_edit_confirm_register_name.text.toString()
            )
        }
    }

    private fun verifyEventTextInInputs() {
        input_edit_name_register_name.addTextChangedListener(watcher)
        input_edit_name_register_name.addTextChangedListener(OnTextWatcher {
            displayNameFailure(null)
        })

        input_edit_password_register_name.addTextChangedListener(watcher)
        input_edit_password_register_name.addTextChangedListener(OnTextWatcher {
            displayPasswordFailure(null)
        })

        input_edit_confirm_register_name.addTextChangedListener(watcher)
        input_edit_confirm_register_name.addTextChangedListener(OnTextWatcher {
            displayPasswordFailure(null)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenner){
            fragmentAttachListenner =  context
        }
    }


    companion object{
        const val KEY_EMAIL = "key_email"
    }

    private val watcher = OnTextWatcher{
        register_np_button_next?.isEnabled =
            textInputIsNotEmptyCheck(input_edit_name_register_name)
                    && textInputIsNotEmptyCheck(input_edit_password_register_name)
                    && textInputIsNotEmptyCheck(input_edit_confirm_register_name)
    }

    fun textInputIsNotEmptyCheck(input: TextView): Boolean{
        return input.text.toString().isNotEmpty()
    }
    override fun showProgress(enabled: Boolean) {
        register_np_button_next.showProgessBar(enabled)
    }

    override fun displayNameFailure(nameError: Int?) {
        txtInput_name__register_name.error = nameError?.let { getString(it)
        }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        txtInput_password_register_name.error = passwordError?.let { getString(it) }
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateSucess(name: String) {
        fragmentAttachListenner?.goToWelcomeScreen(name)
    }

    override fun onDestroy() {
        fragmentAttachListenner = null
        presenter.onDestroy()
        super.onDestroy()
    }


}
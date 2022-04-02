package com.example.cloneinstagram.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.base.DependencyInjector
import com.example.cloneinstagram.common.util.OnTextWatcher
import com.example.cloneinstagram.login.Login
import com.example.cloneinstagram.login.presentation.LoginPresenter
import com.example.cloneinstagram.main.view.MainActivity
import com.example.cloneinstagram.register.view.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), Login.View {
     override lateinit var presenter: Login.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginPresenter(this, DependencyInjector.loginInjector()).also { presenter = it }

        input_edit_email.addTextChangedListener(watcher)
        input_edit_email.addTextChangedListener(OnTextWatcher{
            displayEmailFailure(null)
        })

        input_edit_password.addTextChangedListener(watcher)
        input_edit_password.addTextChangedListener(OnTextWatcher{
            displayPasswordFailure(null)
        })

        login_button_entries.setOnClickListener {
            presenter.login(
                convertedInputToString(input_edit_email),
                convertedInputToString(input_edit_password))
        }
        login_txt_register.setOnClickListener {goToRegisterScreen()}
    }

    private val watcher = OnTextWatcher{
        login_button_entries.isEnabled = (convertedInputToString(input_edit_email).isNotEmpty()
                && convertedInputToString(input_edit_password).isNotEmpty())
    }

    private fun goToRegisterScreen(){
        val intent = Intent(this, RegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun convertedInputToString(text: TextView): String{
        return text.text.toString()
    }

    override fun showProgress(enabled: Boolean) {
        login_button_entries.showProgessBar(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        content_input_email.error = emailError?.let{getString(it)}
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        content_input_password.error = passwordError?.let { getString(it) }
    }

    override fun onUserUnauthorized(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
    }

    override fun onUserAuthenticated() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}


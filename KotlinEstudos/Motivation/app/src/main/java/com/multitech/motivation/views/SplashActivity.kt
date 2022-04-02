package com.multitech.motivation.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.multitech.motivation.R
import com.multitech.motivation.databinding.ActivitySplashBinding
import com.multitech.motivation.utils.SecurityPreferences
import com.multitech.motivation.utils.MotivationConstants
import kotlinx.android.synthetic.main.activity_splash.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var mSecurity: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        mSecurity =  SecurityPreferences(this)

        setContentView(binding.root)
        binding.btnName.setOnClickListener(this)

        verifyUserName()
    }

    private fun navigationScreenPhrase(){
        //Navegação de telas
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun verifyUserName(){
        val name = mSecurity.getStoreString(MotivationConstants.KEY.PERSON_NAME)
        var nameNoNull = name ?: ""
        if(nameNoNull.isNotEmpty()) navigationScreenPhrase()
    }

    private fun handleSave(){
        val name  = editName.text.toString()
        if(name.isEmpty()){
            Toast.makeText(this, "Informe um nome ", Toast.LENGTH_LONG).show()
        }else{
            mSecurity.storeString(MotivationConstants.KEY.PERSON_NAME, name)
            Toast.makeText(this, "Seja Bem vindo(a) $name ", Toast.LENGTH_LONG).show()
            navigationScreenPhrase()
        }
    }

    override fun onClick(v: View) {
        if(v.id == R.id.btnName) handleSave()
    }

}



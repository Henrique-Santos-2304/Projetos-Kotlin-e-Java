package com.multitech.motivation.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.multitech.motivation.R
import com.multitech.motivation.databinding.ActivityMainBinding
import com.multitech.motivation.mock.Mock
import com.multitech.motivation.utils.MotivationConstants
import com.multitech.motivation.utils.SecurityPreferences
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var categorySelected = ""
    private val listsMock = Mock()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSecurityPreferences = SecurityPreferences(this)
        verifyUserName()

        binding.imageAll.setOnClickListener(this)
        binding.imageSun.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.btnNewPrhase.setOnClickListener(this)
    }

    private fun verifyUserName(){
        binding.textUserName.text = mSecurityPreferences.getStoreString(MotivationConstants.KEY.PERSON_NAME)
    }

    private fun shufflePhrase(list: List<String>){
        val numberPhraseSelected = Random().nextInt(list.size)
        binding.randomPrhase.text = list[numberPhraseSelected]
    }

    private fun refreshPhrase(){
        if(categorySelected.isNotEmpty()){
            when(categorySelected){
                "all" -> shufflePhrase(listsMock.allList)
                "sun" -> shufflePhrase(listsMock.sunList)
                "happy" -> shufflePhrase(listsMock.happyList)
            }
        }else{
            Toast.makeText(this, "Escolha uma Categoria ", Toast.LENGTH_LONG).show()
        }
    }

    private fun returnImagesDefault(){
        binding.imageAll.setImageResource(R.drawable.ic_inclusive_allunselected)
        binding.imageSun.setImageResource(R.drawable.ic_sununselected)
        binding.imageHappy.setImageResource(R.drawable.ic_happy_unselected)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imageAll -> {
                categorySelected = "all"
                returnImagesDefault()
                binding.imageAll.setImageResource(R.drawable.ic_inclusive_allselected)
                shufflePhrase(listsMock.allList)
            }
            R.id.imageSun -> {
                categorySelected = "sun"
                returnImagesDefault()
                binding.imageSun.setImageResource(R.drawable.ic_sun)
                shufflePhrase(listsMock.sunList)
            }
            R.id.imageHappy -> {
                categorySelected = "happy"
                returnImagesDefault()
                binding.imageHappy.setImageResource(R.drawable.ic_happy)
                shufflePhrase(listsMock.happyList)
            }
            R.id.btnNewPrhase -> refreshPhrase()
        }
    }
}
package com.example.cloneinstagram.register.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.extensions.replaceFragment
import com.example.cloneinstagram.common.view.CrooperImageFragment
import com.example.cloneinstagram.main.view.MainActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), FragmentAttachListenner{
    lateinit var currentPhoto: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        replaceFragment(R.id.register_fragment,RegisterEmailFragment())
    }

    override fun gotNameAndPasswordScreen(email: String) {
        val fragment = RegisterNamePasswordFragment().apply {
            arguments = Bundle().apply {
                putString(RegisterNamePasswordFragment.KEY_EMAIL, email)
            }
        }
        replaceFragment(R.id.register_fragment,fragment)
    }

    override fun goToWelcomeScreen(name: String) {
      val fragment = RegisterWelcomeFragment().apply {
            arguments = Bundle().apply {
                putString(RegisterWelcomeFragment.KEY_NAME, name)
            }
        }
        replaceFragment(R.id.register_fragment,fragment)
    }

    override fun goToPhotoScreen() {
        replaceFragment(R.id.register_fragment, RegisterPhotoFragment())
    }

    override fun goToMainScreen() {
        val intent= Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri: Uri? -> uri?.let { openCrooperActionCutImage(it) }
    }

    override fun goToGalleryScreen() {
       getContent.launch("image/*")
    }

    private val getCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){ saved ->
        if(saved) openCrooperActionCutImage(currentPhoto)
    }

    override fun goToCameraScreen() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                if(this.resolveActivity(packageManager) != null){
                    val image = tryCreateImage()
                    image?.also {
                        currentPhoto = getDirToSaveImage(it)
                        getCamera.launch(currentPhoto)
                    }
                }
            }
        }

    private fun openCrooperActionCutImage(it: Uri?) {
        val fragment = CrooperImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CrooperImageFragment.KEY_URI, it)
            }
        }
        replaceFragment(R.id.register_fragment, fragment)
    }

    private fun tryCreateImage(): File? {
        return try {
            createImageFile()
        } catch (erro: IOException) {
            Log.e("RegisterActivity", erro.message, erro)
            null
        }
    }

    private fun getDirToSaveImage(file: File) =
        FileProvider.
            getUriForFile(this, "com.example.cloneinstagram.filesProvider", file)

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timesNow = getDateHourNow()
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("Image_$timesNow",".jpg", dir)
    }

    private fun getDateHourNow() =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())


}
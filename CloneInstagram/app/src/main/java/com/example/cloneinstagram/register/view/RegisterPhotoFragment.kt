package com.example.cloneinstagram.register.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.base.DependencyInjector
import com.example.cloneinstagram.common.view.CrooperImageFragment
import com.example.cloneinstagram.common.view.CustomDialog
import com.example.cloneinstagram.register.RegisterPhoto
import com.example.cloneinstagram.register.presentation.RegisterPhotoPresenter
import kotlinx.android.synthetic.main.fragment_photo_user.*

class RegisterPhotoFragment: Fragment(R.layout.fragment_photo_user), RegisterPhoto.View {
    private var fragmentAttachListenner: FragmentAttachListenner? = null
    override lateinit var presenter: RegisterPhoto.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("cropkey"){requestKey, bundle ->
            val uri = bundle.getParcelable<Uri>(CrooperImageFragment.KEY_URI)
            onCropImageResult(uri)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = DependencyInjector.registerEmailInjector()
        presenter = RegisterPhotoPresenter(this, repository)

        btn_jump_photo.setOnClickListener {
            fragmentAttachListenner?.goToMainScreen()
        }

        btn_register_next.isEnabled = true
        btn_register_next.setOnClickListener {
            val customDialog = createDialog()
            customDialog.show()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenner){
            fragmentAttachListenner =  context
        }
    }

    private fun createDialog(): CustomDialog {
        val customDialog = CustomDialog(requireContext())
        customDialog.addButton(R.string.photo, R.string.gallery) {
            handleEventsForItemsDialogs(it)
        }
        return customDialog
    }

    private fun handleEventsForItemsDialogs(it: View) {
        when (it.id) {
            R.string.photo -> {
                fragmentAttachListenner?.goToCameraScreen()
            }
            R.string.gallery -> {
                fragmentAttachListenner?.goToGalleryScreen()
            }
        }
    }

    override fun onDestroy() {
        fragmentAttachListenner = null
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun onCropImageResult(uri: Uri?){
            val bitmap = uri?.let { createBitmapImage(it) }
            image_user_profile_register.setImageBitmap(bitmap)
            uri?.let { presenter.update(uri) }
    }

    private fun createBitmapImage(uri: Uri): Bitmap{
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }
    }

    override fun showProgress(enabled: Boolean) {
        btn_register_next.showProgessBar(enabled)
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onUpdateSucess() {
        fragmentAttachListenner?.goToMainScreen()
    }



}
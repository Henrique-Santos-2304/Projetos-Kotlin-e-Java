package com.example.cloneinstagram.common.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.cloneinstagram.R
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_image_crooper.*
import java.io.File

class CrooperImageFragment:Fragment(R.layout.fragment_image_crooper) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uri = arguments?.getParcelable<Uri>(KEY_URI)

        catchEventsElements()
        loadPhotoWithUriArgument(uri)
    }


    private fun catchEventsElements() {
        btn_image_crop_cancel.setOnClickListener {
            returnToPreviousFragment()
        }

        btn_image_croop_save.setOnClickListener {
            val dir = getDirectoryNativeOfImage()
            if (dir != null) {
                val uriToSaved = createNewUriFromSaveImageCut(dir)
                crooper_container.saveCroppedImageAsync(uriToSaved)
            }
        }

        crooper_container.setOnCropImageCompleteListener { view, result ->
            saveImageForPreviousFragment(result)
        }
    }


    private fun loadPhotoWithUriArgument(uri: Uri?) {
        crooper_container.setAspectRatio(1,1)
        crooper_container.setFixedAspectRatio(true)

        crooper_container.setImageUriAsync(uri)
    }

    private fun getHoursNow() = System.currentTimeMillis().toString()

    private fun createNewUriFromSaveImageCut(dir: File) =
        Uri.fromFile(File(dir.path, "${getHoursNow()}.jpeg"))

    private fun getDirectoryNativeOfImage() =
        context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    private fun saveImageForPreviousFragment(result: CropImageView.CropResult) {
        setFragmentResult("cropkey", bundleOf(KEY_URI to result.uri))
        returnToPreviousFragment()
    }

    private fun returnToPreviousFragment() {
        parentFragmentManager.popBackStack()
    }

    companion object{
       const val KEY_URI= "key_uri"
    }
}
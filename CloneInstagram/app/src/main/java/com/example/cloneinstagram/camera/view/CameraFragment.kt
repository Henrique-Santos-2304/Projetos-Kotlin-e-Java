package com.example.cloneinstagram.camera.view


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.cloneinstagram.R
import com.example.cloneinstagram.common.util.File
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.android.synthetic.main.fragment_camera.*
import java.util.concurrent.Executor

class CameraFragment: Fragment() {
    private lateinit var previewView: PreviewView
    private var imageCapture: ImageCapture? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previewView = image_camera

        camera_app_compat.setOnClickListener {
            savePhoto()
        }
    }
    private fun savePhoto(){
        val imageCapture = imageCapture ?: return
        val photoFile = File.generatedFile(requireActivity())
        val outputFile = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        catchResultEventPickPhoto(imageCapture, outputFile, photoFile)
    }

    private fun catchResultEventPickPhoto(
        imageCapture: ImageCapture,
        outputFile: ImageCapture.OutputFileOptions,
        photoFile: java.io.File
    ) {
        imageCapture.takePicture(
            outputFile,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("Instagram", "Failed in saved Image")
                }

            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("cameraKey"){ _, bundle ->
        if(bundle.getBoolean("startCamera")) startCamera()
        }
    }

    private fun startCamera(){
        val threadContext = ContextCompat.getMainExecutor(requireContext())
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        createCameraPreview(cameraProviderFuture, threadContext)
        }

    private fun createCameraPreview(
        cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
        threadContext: Executor
    ) {
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                preview.setSurfaceProvider(previewView.createSurfaceProvider(camera.cameraInfo))
            } catch (e: Exception) {
                Log.e("TESTE", "Failure initialize camera", e)
            }

        }, threadContext)
    }
}
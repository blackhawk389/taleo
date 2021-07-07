package com.sarah.objectives.features.register.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.data.user.User
import com.sarah.objectives.databinding.FragmentCameraBinding
import com.sarah.objectives.extras.EmptyRepository
import com.sarah.objectives.utils.routeTo
import com.sarah.objectives.utils.showToast
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : BaseFragment<FragmentCameraBinding, EmptyRepository>() {

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private  var user: User? = null

    override fun getRepository(): EmptyRepository = EmptyRepository()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCameraBinding = FragmentCameraBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        setupArguments()
        outputDirectory = getOutputDirectory()
        startCamera()
        setupListeners()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun setupListeners() {
        cameraShutter.setOnClickListener {
            takePhoto()
        }
    }

    private fun setupArguments() {
        arguments?.let {
            user = it["user"] as? User?
        }
    }
    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = getPreview()
            imageCapture = ImageCapture.Builder().build()
            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch (exc: Exception) {
                showToast(exc.toString())
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun getPreview() = Preview.Builder()
        .build()
        .also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File( requireContext().cacheDir, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg")

        /* Create output options object which contains file + metadata */
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {

                override fun onError(exc: ImageCaptureException) {
                    showToast(exc.message.toString())
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val isFromCamera = true
                    val bundle = bundleOf("isFromCamera" to isFromCamera,"captured_image" to savedUri,"user" to user)
                   // routeTo(R.id.action_cameraFragment_to_profileBottomSheet, bundle)
                }
            })

    }

    @Suppress("DEPRECATION")
    private fun getOutputDirectory(): File {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

}
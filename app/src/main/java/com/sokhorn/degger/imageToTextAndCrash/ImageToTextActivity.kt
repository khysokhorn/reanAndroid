package com.sokhorn.degger.imageToTextAndCrash

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.mlkit.vision.text.Text
import com.sokhorn.degger.ItemCallBack
import com.sokhorn.degger.R
import com.sokhorn.degger.databinding.CameraXActivityBinding
import com.sokhorn.degger.imageToTextAndCrash.Constants.FILENAME_FORMAT
import com.sokhorn.degger.imageToTextAndCrash.Constants.REQUEST_CODE_CAMERA_PERMISSION
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit

private const val REQUEST_CODE = 13
private lateinit var filePhoto: File
private const val FILE_NAME = "photo.jpg"

class ImageToTextActivity : AppCompatActivity() {
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var binding: CameraXActivityBinding
    private lateinit var imageToText: ImageToText
    private val TAG = "ImageToTextActivity"

    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val a: String? = null
        binding = DataBindingUtil.setContentView(this, R.layout.camera_x_activity)
        imageToText = ImageToText(this)

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.cameraCaptureButton.setOnClickListener {
            chooseImageGallery()
        }
        binding.btnBoom.setOnClickListener {
            FirebaseCrashlytics.getInstance().setCustomKey(
                "int_key",
                "this is log form android"
            )
            binding.image.setImageBitmap(getBitmapFromURL("https://i1.wp.com/leaderreaderjournal.com/wp-content/uploads/2021/01/dog.jpg?resize=768%2C385&ssl=1"))
        }
        FirebaseCrashlytics.getInstance().setCustomKey(
            "==> key123",
            "Hey this is error from mobile"
        )
        // Set int_key from 50 to 100.
    }

    // choose image
    private fun getPhotoFile(finaName: String): File {
        val dirStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(finaName, ".jpg", dirStorage)
    }

    private fun openCamera() {
        val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        filePhoto = getPhotoFile(FILE_NAME)
        val providerFile =
            FileProvider.getUriForFile(
                this@ImageToTextActivity,
                "com.sokhorn.degger",
                filePhoto
            )
        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        if (photoIntent.resolveActivity(this@ImageToTextActivity.packageManager) != null) {
            startActivityForResult(photoIntent, REQUEST_CODE)
        } else {
            Toast.makeText(
                this@ImageToTextActivity,
                "Camera could not open",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.action = Intent.ACTION_GET_CONTENT;
        val chooserIntent = Intent.createChooser(intent, "Select Image")
        startActivityForResult(chooserIntent, IMAGE_CHOOSE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImageGallery()
                    Log.d("TAG", "onRequestPermissionsResult: hi")
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // choose image
        if (requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: image ${data?.clipData}")
            val datas = data?.clipData
            val uri = data?.data
//            if (datas != null) {
//                val itemCount = datas.itemCount
//                Log.d(TAG, "onActivityResult: item count $itemCount")
//                for (i in 0 until itemCount) {
//                    val url = datas.getItemAt(i).uri
//                    imageBitmaps.add(helperClass.fromUriToBitmap(this, url))
//                    Log.d(TAG, "onActivityResult: url image $url")
//                }
//                imageAdapter.notifyDataSetChanged()
//            }
            // user select only one pic
            val appUri = data?.data
            if (appUri != null) {
                val bitmap = imageToText.uriToBitMap(this, appUri)
                binding.image.setImageBitmap(bitmap)
                binding.progressCircular.visibility = View.VISIBLE
                imageToText.recognizeText(bitmap, object : ItemCallBack {
                    override fun <D> itemCallBack(d: D) {
                        val visionText = d as Text
                        binding.progressCircular.visibility = View.GONE
                        for (block in visionText.textBlocks) {
                            val boundingBox = block.boundingBox
                            val cornerPoints = block.cornerPoints
                            val text = block.text
                            for (line in block.lines) {
                                Log.d(
                                    TAG, "itemCallBack: line \n ==> ${
                                        line.elements.map {
                                            Log.d(
                                                TAG,
                                                "itemCallBack: item value \n\n =>> ${it.text} \n\n"
                                            )
                                            if (imageToText.checkEmail(it.text)) {
                                                binding.tvEmail.text = it.text
                                            }
                                        }
                                    } \n"
                                )
//                                for (element in line.elements) {
//                                    binding.tvEmail.text = element.text
//                                }
                            }
                        }
                    }
                })
            }
        }
    }

    // end choose image

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })
    }

    private fun requestPermission() {

        if (CameraUtility.hasCameraPermissions(this)) {
            startCamera()
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the camera permission to use this app",
                REQUEST_CODE_CAMERA_PERMISSION,
                Manifest.permission.CAMERA
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the camera permission to use this app",
                REQUEST_CODE_CAMERA_PERMISSION,
                Manifest.permission.CAMERA

            )
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()


            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }







    private fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            FirebaseCrashlytics.getInstance().setCustomKey("convertBitmap", " error with ${e.message}")
            null
        }
    }

}
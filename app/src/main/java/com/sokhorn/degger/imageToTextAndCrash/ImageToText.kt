package com.sokhorn.degger.imageToTextAndCrash

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.TextRecognizerOptions
import com.sokhorn.degger.ItemCallBack
import java.util.regex.Pattern

class ImageToText(context: Context) {
    private val TAG = "===>ImageToText"
    fun recognizeText(bitmap: Bitmap, itemCallBack: ItemCallBack) {
        //val image = InputImage.fromBitmap(drawableToBitmap(imageDrawable), 0)
        val image = InputImage.fromBitmap(bitmap, 0)
        // [START get_detector_default]
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        // [END get_detector_default]

        // [START run_detector]
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                // [START_EXCLUDE]
                // [START get_text]
                processTextBlock(visionText)
                itemCallBack.itemCallBack(visionText)
                for (block in visionText.textBlocks) {
                    val boundingBox = block.boundingBox
                    val cornerPoints = block.cornerPoints
                    val text = block.text
                    Log.d(TAG, "recognizeText: block $block")
                    for (line in block.lines) {
                        for (element in line.elements) {
                            Log.d(TAG, "recognizeText: element text $text")
                        }
                    }
                }
                // [END get_text]
                // [END_EXCLUDE]
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "recognizeText: error with $e")
            }
        // [END run_detector]
    }

    private fun processTextBlock(result: Text) {
        // [START mlkit_process_text_block]
        val resultText = result.text
        for (block in result.textBlocks) {
            val blockText = block.text
            val blockCornerPoints = block.cornerPoints
            val blockFrame = block.boundingBox
            for (line in block.lines) {
                val lineText = line.text
                val lineCornerPoints = line.cornerPoints
                val lineFrame = line.boundingBox
                for (element in line.elements) {
                    val elementText = element.text
                    val elementCornerPoints = element.cornerPoints
                    val elementFrame = element.boundingBox
                }
            }
        }
        // [END mlkit_process_text_block]
    }

    private fun getTextRecognizer(): TextRecognizer {
        // [START mlkit_local_doc_recognizer]
        return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        // [END mlkit_local_doc_recognizer]
    }

//    private fun drawableToBitmap(imageDrawable: Int): Bitmap {
//       // return BitmapFactory.decodeResource(context.resources, imageDrawable)
//    }

    public fun uriToBitMap(c: Context, uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(c.contentResolver, uri);
    }

  private  val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
     fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

}
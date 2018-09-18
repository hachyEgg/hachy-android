package ms.imagine.foodiemate.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log

import org.jetbrains.annotations.NonNls

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Image {
    private const val TAG = "Image"
    private val MEDIA_TYPE_IMAGE = 1
    private val MEDIA_TYPE_VIDEO = 2
    @NonNls
    private val YYYY_M_MDD_H_HMMSS = "yyyyMMdd_HHmmss"


    fun createImage(suri: Uri): Uri? {
        val myFile = File(suri.toString())
        val bmp = BitmapFactory.decodeFile(myFile.absolutePath)
        return createImage(bmp)
    }

    fun createImage(data: ByteArray): Uri? {
        val bmp = BitmapFactory.decodeByteArray(data, 0, data.size)
        return createImage(bmp)
    }

    fun createImage(bmp: Bitmap): Uri? {
        val pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE)

        val uri = Uri.fromFile(pictureFile)
        if (pictureFile == null) {
            println("Error creating media file, check storage permissions: ")
            return null
        }

        val bos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bos)

        try {
            println("IMAGE: to write files")
            val fos = FileOutputStream(pictureFile)
            fos.write(bos.toByteArray())
            fos.close()
            println("IMAGE: write files finished" + SimpleDateFormat("HH:mm:ss:SSS").format(Date(System.currentTimeMillis())))
            return uri
        } catch (e: FileNotFoundException) {
            println("IMAGE: File not found: " + e.message)
        } catch (e: IOException) {
            println("IMAGE: Error accessing file: " + e.message)
        }

        return null
    }

    private fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }



    /** Create a File for saving an image or video  */
    private fun getOutputMediaFile(type: Int): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Hachy")
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory")
                return null
            }
        }

        Log.w(TAG, "HasFolder")

        // Create a media file name
        val timeStamp = SimpleDateFormat(YYYY_M_MDD_H_HMMSS, Locale.ENGLISH).format(Date())
        val mediaFile: File
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = File(mediaStorageDir.path + File.separator +
                    "IMG_" + timeStamp + ".jpg")
            Log.w(TAG, mediaFile.absolutePath)
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = File(mediaStorageDir.path + File.separator +
                    "VID_" + timeStamp + ".mp4")
        } else {
            return null
        }
        return mediaFile
    }
}

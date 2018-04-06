package ms.imagine.foodiemate.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.hardware.Camera
import android.hardware.Camera.PictureCallback
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.Surface
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.utils.BgData
import ms.imagine.foodiemate.utils.Eulog
import ms.imagine.foodiemate.views.ImageSurfaceView
import ms.imagine.foodiemate.data.Egg


class CameraActivity : AppCompatActivity(), View.OnClickListener {

    private var mImageSurfaceView: ImageSurfaceView? = null
    private var camera: Camera? = null

    private var cameraPreviewLayout: FrameLayout? = null

    internal var pictureCallback: PictureCallback = PictureCallback { data, camera ->
        /* noo need to convert here
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if(bitmap==null){
                Toast.makeText(CameraActivity.this, "Captured image is empty", Toast.LENGTH_LONG).show();
                return;
            }
            */
        // sendBack();
        //capturedImageHolder.setImageBitmap(scaleDownBitmapImage(bitmap, 300, 300 ));
        Eulog.w("pictureCallBackRegistered")
        val pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE)

        val uri = Uri.fromFile(pictureFile)
        if (pictureFile == null) {
            Eulog.w("Error creating media file, check storage permissions: ")
            return@PictureCallback
        }


        try {
            Eulog.d("$TAG: worked to fileOutput")
            val fos = FileOutputStream(pictureFile)
            fos.write(data)
            fos.close()
            Eulog.d("$TAG: WriteFiles Finished")
            sendBack(uri)
        } catch (e: FileNotFoundException) {
            Eulog.d(TAG + ": File not found: " + e.message)
        } catch (e: IOException) {
            Eulog.d(TAG + ": Error accessing file: " + e.message)
        } finally {
            Eulog.w("$TAG: Cam_Finished()")
            //finish();
        }
    }
    //private ImageView capturedImageHolder;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        cameraPreviewLayout = findViewById(R.id.camera_preview)
        //capturedImageHolder = findViewById(R.id.captured_image);

        camera = checkDeviceCamera()

        //STEP #1: Get rotation degrees
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info)
        val rotation = this.windowManager.defaultDisplay.rotation
        var degrees = 0
        when (rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }//Natural orientation
        //Landscape left
        //Upside down
        //Landscape right
        val rotate = (info.orientation - degrees + 360) % 360

        //STEP #2: Set the 'rotation' parameter
        val params = camera!!.parameters
        params.setRotation(rotate)
        camera!!.parameters = params

        mImageSurfaceView = ImageSurfaceView(this@CameraActivity, camera!!)
        cameraPreviewLayout!!.addView(mImageSurfaceView)

        val captureButton = findViewById<ImageButton>(R.id.button_tak_pic)
        // lol
        //cameraPreviewLayout.setOnClickListener(this);
        captureButton.setOnClickListener(this)
    }

    private fun checkDeviceCamera(): Camera? {
        var mCamera: Camera? = null
        try {
            mCamera = Camera.open()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return mCamera
    }

    private fun scaleDownBitmapImage(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private fun sendBack(uri: Uri) {
        Eulog.w(uri.toString())
        if (BgData.write(this, MainActivity.TAKE_PIC_FINISHED, uri.toString())) {
            Eulog.w("value written")
            val i = Intent(this@CameraActivity, DetailActivity::class.java)
            i.putExtra("isNewEgg", true)
            i.putExtra("Egg", Egg("coo", System.currentTimeMillis(), 0))
            startActivity(i)
        }
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onClick(view: View) {
        camera!!.takePicture(null, null, pictureCallback)
    }

    companion object {
        val MEDIA_TYPE_IMAGE = 1
        val MEDIA_TYPE_VIDEO = 2

        val TAG = "CameraActivity"

        // Media File Creation
        /** Create a file Uri for saving an image or video  */
        private fun getOutputMediaFileUri(type: Int): Uri {
            return Uri.fromFile(getOutputMediaFile(type))
        }

        /** Create a File for saving an image or video  */
        private fun getOutputMediaFile(type: Int): File? {
            // To be safe, you should check that the SDCard is mounted
            // using Environment.getExternalStorageState() before doing this.

            val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "Foodiemate")
            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Eulog.d("failed to create directory")
                    return null
                }
            }

            Eulog.w("HasFolder")

            // Create a media file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
            val mediaFile: File
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = File(mediaStorageDir.path + File.separator +
                        "IMG_" + timeStamp + ".jpg")
                Eulog.w(mediaFile.absolutePath)
            } else if (type == MEDIA_TYPE_VIDEO) {
                mediaFile = File(mediaStorageDir.path + File.separator +
                        "VID_" + timeStamp + ".mp4")
            } else {
                return null
            }
            return mediaFile
        }
    }
}
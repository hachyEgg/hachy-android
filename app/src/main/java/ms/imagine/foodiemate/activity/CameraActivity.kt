package ms.imagine.foodiemate.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.hardware.Camera
import android.hardware.Camera.PictureCallback
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.data.Image
import ms.imagine.foodiemate.utils.Eulog
import ms.imagine.foodiemate.views.ImageSurfaceView


class CameraActivity : BaseActivity(), View.OnClickListener {
    private var mImageSurfaceView: ImageSurfaceView? = null
    private lateinit var camera: Camera
    private var cameraPreviewLayout: FrameLayout? = null

    private var pictureCallback: PictureCallback = PictureCallback { data, camera ->
        Eulog.w("pictureCallBackRegistered")
        val uri = Image.createImage(data)
        sendBack(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        cameraPreviewLayout = findViewById(R.id.camera_preview)

        camera = checkDeviceCamera()!!


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
        val params = camera.parameters
        params.setRotation(rotate)

        params.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
        camera.parameters = params

        mImageSurfaceView = ImageSurfaceView(this@CameraActivity, camera)
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

    private fun sendBack(uri: Uri) {
        Eulog.w(uri.toString())
        setBg(this)
        if (uri != null && bG.write(MainActivity.TAKE_PIC_FINISHED, uri.toString())) {
            Eulog.w("value written")
            val i = Intent(this@CameraActivity, DetailActivity::class.java)
            i.putExtra("isNewEgg", true)
            i.putExtra("Egg", Egg("coo", System.currentTimeMillis(), 0))
            startActivity(i)
        }
        finish()
    }

    override fun onClick(view: View) {
        camera.takePicture(null, null, pictureCallback)
    }

    companion object {
        val TAG = "CameraActivity"
    }
}
package ms.imagine.foodiemate.activity;

package ms.imagine.foodiemate.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.hardware.Camera
import android.hardware.Camera.PictureCallback
import android.icu.text.IDNA;
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.data.Eggs
import ms.imagine.foodiemate.data.Image
import ms.imagine.foodiemate.views.ImageSurfaceView


public class CameraActivity extends BaseActivity implements View.OnClickListener {
    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;
    private FrameLayout cameraPreviewLayout;

    private var pictureCallback: PictureCallback = PictureCallback { data, camera ->
            Log.w(TAG, "pictureCallBackRegistered")
        val uri = Image.createImage(data)
        sendBack(uri)
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cameraPreviewLayout = findViewById(R.id.camera_preview);

        camera = checkDeviceCamera();


                //STEP #1: Get rotation degrees
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }//Natural orientation
        //Landscape left
        //Upside down
        //Landscape right
        int rotate = (info.orientation - degrees + 360) % 360;

        //STEP #2: Set the 'rotation' parameter
        Camera.Parameters params = camera.getParameters();
        params.setRotation(rotate);

        params.setFocusMode (Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(params);

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
        Log.w(TAG, uri.toString())
        Log.w(TAG, "value written")
        val i = Intent(this@CameraActivity, DetailActivity::class.java)
        val timestamp = System.currentTimeMillis()
        val eggs = Eggs("coo", timestamp, 0, uri)

        // New Egg Processing technique
        eggs.insertSnap(Egg(uri.lastPathSegment,0 ,timestamp ))
        i.putExtra("Egg", eggs)
        startActivity(i)
        finish()
    }

    override fun onClick(view: View) {
        camera.takePicture(null, null, pictureCallback)
    }

    companion object {
        const val TAG = "CameraActivity"
    }
}
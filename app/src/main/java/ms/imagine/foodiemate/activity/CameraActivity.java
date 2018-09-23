package ms.imagine.foodiemate.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import ms.imagine.foodiemate.R;
import ms.imagine.foodiemate.data.Egg;
import ms.imagine.foodiemate.data.Eggs;
import ms.imagine.foodiemate.data.Image;
import ms.imagine.foodiemate.views.ImageSurfaceView;


public class CameraActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CameraActivity";
    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;
    private FrameLayout cameraPreviewLayout;

    private PictureCallback pictureCallback;


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

        mImageSurfaceView = new ImageSurfaceView(CameraActivity.this, camera);
        cameraPreviewLayout.addView(mImageSurfaceView);

        ImageButton captureButton = findViewById(R.id.button_tak_pic);
                // lol
                //cameraPreviewLayout.setOnClickListener(this);
        captureButton.setOnClickListener(this);
    }

    private Camera checkDeviceCamera() {
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return mCamera;
    }

    private void sendBack(Uri uri) {
        Log.w(TAG, uri.toString());
        Log.w(TAG, "value written");
        Intent i = new Intent(CameraActivity.this, DetailActivity.class);
        long timestamp = System.currentTimeMillis();
        Eggs eggs = new Eggs("coo", timestamp, 0,"", uri);

        // New Egg Processing technique
        eggs.insertSnap(new Egg(uri.getLastPathSegment(),0 ,timestamp ));
        i.putExtra("Egg", eggs);
        startActivity(i);
        finish();
    }

    @Override public void onClick(View view) {
        camera.takePicture(null, null, (data, camera) -> {
                Log.w(TAG, "pictureCallBackRegistered");
                Uri uri = Image.createImage(data);
                sendBack(uri);
        });
    }
}
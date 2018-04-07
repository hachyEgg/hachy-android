package ms.imagine.foodiemate.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import ms.imagine.foodiemate.R;
import ms.imagine.foodiemate.data.Image;
import ms.imagine.foodiemate.utils.BgData;
import ms.imagine.foodiemate.utils.Eulog;
import ms.imagine.foodiemate.views.ImageSurfaceView;
import ms.imagine.foodiemate.data.Egg;


public class CameraActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "CameraActivity";
    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;
    private FrameLayout cameraPreviewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            case Surface.ROTATION_0: degrees = 0; break; //Natural orientation
            case Surface.ROTATION_90: degrees = 90; break; //Landscape left
            case Surface.ROTATION_180: degrees = 180; break;//Upside down
            case Surface.ROTATION_270: degrees = 270; break;//Landscape right
        }
        int rotate = (info.orientation - degrees + 360) % 360;

        //STEP #2: Set the 'rotation' parameter
        Camera.Parameters params = camera.getParameters();
        params.setRotation(rotate);
        camera.setParameters(params);

        mImageSurfaceView = new ImageSurfaceView(CameraActivity.this, camera);
        cameraPreviewLayout.addView(mImageSurfaceView);

        ImageButton captureButton = findViewById(R.id.button_tak_pic);
        // lol
        //cameraPreviewLayout.setOnClickListener(this);
        captureButton.setOnClickListener(this);
    }
    private Camera checkDeviceCamera(){
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCamera;
    }

    PictureCallback pictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Eulog.INSTANCE.w("pictureCallBackRegistered");
            Uri uri = Image.createImage(data);
            sendBack(uri);
        }
    };

    private void sendBack(Uri uri){
        Eulog.INSTANCE.w( uri.toString());
        if (uri != null && BgData.INSTANCE.write(this, MainActivity.TAKE_PIC_FINISHED, uri.toString())){
            Eulog.INSTANCE.w( "value written");
            Intent i = new Intent(CameraActivity.this, DetailActivity.class);
            i.putExtra("isNewEgg", true);
            i.putExtra("Egg", new Egg("coo", System.currentTimeMillis(), 0));
            startActivity(i);
        }
        finish();
    }

    @Override
    public void onClick(View view) {
        camera.takePicture(null, null, pictureCallback);
    }
}
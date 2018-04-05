package ms.imagine.foodiemate.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ms.imagine.foodiemate.R;
import ms.imagine.foodiemate.utils.BgData;
import ms.imagine.foodiemate.utils.Eulog;
import ms.imagine.foodiemate.views.ImageSurfaceView;
import ms.imagine.foodiemate.data.Egg;


public class CameraActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static final String TAG = "CameraActivity";

    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;

    private FrameLayout cameraPreviewLayout;
    //private ImageView capturedImageHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cameraPreviewLayout = findViewById(R.id.camera_preview);
        //capturedImageHolder = findViewById(R.id.captured_image);

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
            /* noo need to convert here
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if(bitmap==null){
                Toast.makeText(CameraActivity.this, "Captured image is empty", Toast.LENGTH_LONG).show();
                return;
            }
            */
            // sendBack();
            //capturedImageHolder.setImageBitmap(scaleDownBitmapImage(bitmap, 300, 300 ));
            Eulog.INSTANCE.w("pictureCallBackRegistered");
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            Uri uri = Uri.fromFile(pictureFile);
            if (pictureFile == null){
                Eulog.INSTANCE.w("Error creating media file, check storage permissions: ");
                return;
            }


            try {
                Eulog.INSTANCE.d(TAG+ ": worked to fileOutput");
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                Eulog.INSTANCE.d(TAG + ": WriteFiles Finished");
                sendBack(uri);
            } catch (FileNotFoundException e) {
                Eulog.INSTANCE.d( TAG + ": File not found: " + e.getMessage());
            } catch (IOException e) {
                Eulog.INSTANCE.d(TAG + ": Error accessing file: " + e.getMessage());
            } finally {
                Eulog.INSTANCE.w(TAG + ": Cam_Finished()");
                //finish();
            }
        }
    };

    private Bitmap scaleDownBitmapImage(Bitmap bitmap, int newWidth, int newHeight){
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return resizedBitmap;
    }

    private void sendBack(Uri uri){
        Eulog.INSTANCE.w( uri.toString());
        if (BgData.INSTANCE.write(this, MainActivity.TAKE_PIC_FINISHED, uri.toString())){
            Eulog.INSTANCE.w( "value written");
            Intent i = new Intent(CameraActivity.this, DetailActivity.class);
            i.putExtra("isNewEgg", true);
            i.putExtra("Egg", new Egg("coo", System.currentTimeMillis(), 0));
            startActivity(i);
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        camera.takePicture(null, null, pictureCallback);
    }

    // Media File Creation
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Foodiemate");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Eulog.INSTANCE.d("failed to create directory");
                return null;
            }
        }

        Eulog.INSTANCE.w( "HasFolder");

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
            Eulog.INSTANCE.w(mediaFile.getAbsolutePath());
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }
}
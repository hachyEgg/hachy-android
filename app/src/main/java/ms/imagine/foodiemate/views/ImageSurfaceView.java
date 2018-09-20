package ms.imagine.foodiemate.views;

/**
 * Created by eugen on 3/27/2018.
 */

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;

public class ImageSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Camera camera;

    public ImageSurfaceView (Context context, Camera camera) {
        super(context);
        camera.setDisplayOrientation(90);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.camera = camera;
    }


    @Override public void surfaceCreated(SurfaceHolder holder) {
        try {
            this.camera.setPreviewDisplay(holder);
            this.camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        this.camera.stopPreview();
        this.camera.release();
    }
}
package ms.imagine.foodiemate.views

/**
 * Created by eugen on 3/27/2018.
 */

import android.hardware.Camera
import android.content.Context
import android.view.Display
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager

import java.io.IOException

class ImageSurfaceView(context: Context, private val camera: Camera) : SurfaceView(context), SurfaceHolder.Callback {
    private val surfaceHolder: SurfaceHolder

    init {
        camera.setDisplayOrientation(90)
        this.surfaceHolder = holder
        this.surfaceHolder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            this.camera.setPreviewDisplay(holder)
            this.camera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        this.camera.stopPreview()
        this.camera.release()
    }
}
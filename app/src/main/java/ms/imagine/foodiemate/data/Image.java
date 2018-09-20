package ms.imagine.foodiemate.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import org.jetbrains.annotations.NonNls;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Image {
    private static final String TAG = "Image";
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int MEDIA_TYPE_VIDEO = 2;

    @NonNls
    private static final String YYYY_M_MDD_H_HMMSS = "yyyyMMdd_HHmmss";

    public static Uri createImage(Uri suri) {
        File myFile = new File(suri.toString());
        Bitmap bmp = BitmapFactory.decodeFile(myFile.getAbsolutePath());
        return createImage(bmp);
    }

    public static Uri createImage(byte[] data) {
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        return createImage(bmp);
    }

    private static Uri createImage(Bitmap bmp) {
        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

        Uri uri = Uri.fromFile(pictureFile);
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return null;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bos);

        try {
            Log.d(TAG, "IMAGE: to write files");
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(bos.toByteArray());
            fos.close();
            Log.d(TAG, "IMAGE: write files finished" + new SimpleDateFormat("HH:mm:ss:SSS").format(new Date(System.currentTimeMillis())));
            return uri;
        } catch (FileNotFoundException e ) {
            Log.d(TAG, "IMAGE: File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "IMAGE: Error accessing file: " + e.getMessage());
        }

        return null;
    }

    private static Uri getOutputMediaFileUri(int type)  {
        return Uri.fromFile(getOutputMediaFile(type));
    }



    /** Create a File for saving an image or video  */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Hachy");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        Log.w(TAG, "HasFolder");

        // Create a media file name
        String timeStamp = new SimpleDateFormat(YYYY_M_MDD_H_HMMSS, Locale.ENGLISH).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
            Log.w(TAG, mediaFile.getAbsolutePath());
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }
}

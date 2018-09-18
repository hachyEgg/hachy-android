package ms.imagine.foodiemate.presenters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import ms.imagine.foodiemate.callbacks.StWriteCallBacks;
import java.text.SimpleDateFormat;
import java.util.*;

public class FbStorageWrite extends FbStoragePresenter {
    private StWriteCallBacks callback;
    private static final String TAG = "FbStorageWrite";

    public FbStorageWrite(StWriteCallBacks callback){
        this.callback = callback;
    }

    public void uploadImage(Uri uri) {
        StorageReference riversRef = imagesRef.child(uri.getLastPathSegment());
        Task uploadTask = riversRef.putFile(uri);

        Log.d (TAG, String.format( "UPLOADING(%n): started", new SimpleDateFormat("HH:mm:ss:SSS", Locale.US).format(new Date(System.currentTimeMillis()))));

        uploadTask.addOnSuccessListener(task -> {
                    Uri downloadUrl = ((UploadTask.TaskSnapshot)task).getDownloadUrl();
                    callback.uploadSuccess(downloadUrl);
                    Log.d(TAG, String.format("UPLOADING(%n): finished", new SimpleDateFormat("HH:mm:ss:SSS", Locale.US).format(new Date(System.currentTimeMillis()))));
                }
        );

        uploadTask.addOnProgressListener {
        taskSnapshot ->
        println ("UPLOADING("+ SimpleDateFormat("HH:mm:ss:SSS").format(Date(System.currentTimeMillis()))+ "): " + taskSnapshot.bytesTransferred)
        }
        }

        fun downloadImage(context: Context, imgURL: String, imgView:ImageView){
        Glide.with(context /* context */)
        .using<StorageReference>(FirebaseImageLoader())
        .load(imagesRef.child(imgURL))
        .into(imgView)
        }
        }
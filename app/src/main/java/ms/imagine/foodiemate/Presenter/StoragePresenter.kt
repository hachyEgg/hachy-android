package ms.imagine.foodiemate.Presenter

import android.widget.Toast
import ms.imagine.foodiemate.activity.MainActivity
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.UUID.randomUUID
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import ms.imagine.foodiemate.views.IDetailView


class StorageUploadPresenter {
    internal var fbStore: FirebaseStorage
    internal var imagesRef: StorageReference
    internal var mContext: Context
    internal var mView: IDetailView

    constructor(context: Context, view: IDetailView){
        mContext = context
        mView = view
        fbStore = FirebaseStorage.getInstance()
        imagesRef = fbStore.getReference().child("image")
    }

    fun imgReference():StorageReference{
        return imagesRef
    }
    fun uploadImage(uri: Uri) {
        val riversRef = imagesRef.child(uri.lastPathSegment)
        var uploadTask = riversRef.putFile(uri)

        uploadTask.addOnFailureListener({
            it->it.printStackTrace()
            mView.uploadFailed()
        }).addOnSuccessListener({ taskSnapshot ->
            val downloadUrl = taskSnapshot.downloadUrl
            mView.uploadSuccess(downloadUrl);
            Log.w("EUGWARN_CAM", "successful pic")
        })
    }

    private fun downloadImage(){

    }
}
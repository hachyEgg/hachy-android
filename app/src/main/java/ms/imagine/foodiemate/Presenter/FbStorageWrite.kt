package ms.imagine.foodiemate.Presenter

import android.content.Context
import android.media.Image
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.callbacks.StWriteCallBacks

class FbStorageWrite (private val callback: StWriteCallBacks): FbStoragePresenter() {

    fun uploadImage(uri: Uri) {
        val riversRef = imagesRef.child(uri.lastPathSegment)
        var uploadTask = riversRef.putFile(uri)

        uploadTask.addOnFailureListener({
            it->it.printStackTrace()
            callback.uploadFailed()
        }).addOnSuccessListener({ taskSnapshot ->
            val downloadUrl = taskSnapshot.downloadUrl
            callback.uploadSuccess(downloadUrl);
            Log.w("EUGWARN_CAM", "successful pic")
        })
    }

    fun downloadImage(context: Context, imgURL: String, imgView:ImageView){
        Glide.with(context /* context */)
                .using<StorageReference>(FirebaseImageLoader())
                .load(imagesRef.child(imgURL))
                .into(imgView)
    }
}
package ms.imagine.foodiemate.Presenter

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import ms.imagine.foodiemate.callbacks.StWriteCallBacks
import ms.imagine.foodiemate.utils.Time

class FbStorageWrite (private val callback: StWriteCallBacks): FbStoragePresenter() {

    fun uploadImage(uri: Uri) {
        val riversRef = imagesRef.child(uri.lastPathSegment)
        var uploadTask = riversRef.putFile(uri)
        println ("UPLOADING("+ Time.timehrs()+ "): started")
        uploadTask.addOnSuccessListener({ taskSnapshot ->
            val downloadUrl = taskSnapshot.downloadUrl
            callback.uploadSuccess(downloadUrl)
            println("UPLOADING("+ Time.timehrs()+ "): finished")
        })

        uploadTask.addOnProgressListener {
            taskSnapshot ->
            println ("UPLOADING("+ Time.timehrs()+ "): " + taskSnapshot.bytesTransferred)
        }
    }

    fun downloadImage(context: Context, imgURL: String, imgView:ImageView){
        Glide.with(context /* context */)
                .using<StorageReference>(FirebaseImageLoader())
                .load(imagesRef.child(imgURL))
                .into(imgView)
    }
}
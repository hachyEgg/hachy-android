package ms.imagine.foodiemate.Presenter

import android.net.Uri
import android.util.Log
import ms.imagine.foodiemate.views.StWriteCallBacks

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

    private fun downloadImage(){

    }
}
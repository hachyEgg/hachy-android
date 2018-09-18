package ms.imagine.foodiemate.presenters

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import java.io.InputStream
import java.net.URL


class FbStorageRead (): FbStoragePresenter() {

    fun downloadImage(context: Context, imgURL: String, imgView: ImageView){
        Glide.with(context /* context */)
                .using<StorageReference>(FirebaseImageLoader())
                .load(imagesRef.child(imgURL))
                .into(imgView)
    }

    fun LoadImageFromWebOperations(url: String): Drawable? {
        try {
            val ois = URL(url).content as InputStream
            return Drawable.createFromStream(ois, "src name")
        } catch (e: Exception) {
            return null
        }
    }

}
package ms.imagine.foodiemate.Presenter

import android.net.Uri
import ms.imagine.foodiemate.views.StReadCallBacks
import android.graphics.drawable.Drawable
import java.io.InputStream
import java.net.URL


class FbStorageRead (private val callback: StReadCallBacks): FbStoragePresenter() {

    fun downloadImage(uri: Uri) {

    }

    fun LoadImageFromWebOperations(url: String): Drawable? {
        try {
            val ois = URL(url).getContent() as InputStream
            return Drawable.createFromStream(ois, "src name")
        } catch (e: Exception) {
            return null
        }
    }
}
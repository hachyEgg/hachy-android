package ms.imagine.foodiemate.callbacks

import android.net.Uri

interface StWriteCallBacks {
    fun uploadFailed()
    fun uploadSuccess(uri: Uri?)
}
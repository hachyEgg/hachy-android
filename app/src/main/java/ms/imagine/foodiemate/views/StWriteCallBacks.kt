package ms.imagine.foodiemate.views

import android.net.Uri

interface StWriteCallBacks {
    fun uploadFailed()
    fun uploadSuccess(uri: Uri?)
}
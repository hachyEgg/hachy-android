package ms.imagine.foodiemate.callbacks

import android.net.Uri

interface StReadCallBacks {
    fun downloadFailed()
    fun downloadSuccess(uri: Uri?)
}
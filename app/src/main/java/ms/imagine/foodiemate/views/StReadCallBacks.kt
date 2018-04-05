package ms.imagine.foodiemate.views

import android.net.Uri

interface StReadCallBacks {
    fun downloadFailed()
    fun downloadSuccess(uri: Uri?)
}
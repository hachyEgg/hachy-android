package ms.imagine.foodiemate.views

import android.net.Uri

interface IDetailView {
    fun uploadFailed()
    fun uploadSuccess(uri: Uri?)
}
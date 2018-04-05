package ms.imagine.foodiemate.Presenter

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

open class FbStoragePresenter {
    var imagesRef: StorageReference

    init{imagesRef = FirebaseStorage.getInstance().getReference().child("image")}

    fun imgReference():StorageReference{
        return imagesRef
    }
}
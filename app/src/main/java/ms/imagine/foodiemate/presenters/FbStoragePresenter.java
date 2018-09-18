package ms.imagine.foodiemate.presenters;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FbStoragePresenter {
    StorageReference imagesRef = FirebaseStorage.getInstance().getReference().child("image");
}

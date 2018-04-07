package ms.imagine.foodiemate.Presenter

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

open class FbDatabasePresenter(_uid: String?){
    var firebaseDB:DatabaseReference = FirebaseDatabase.getInstance().reference.child(_uid)
}
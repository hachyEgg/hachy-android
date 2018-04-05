package ms.imagine.foodiemate.Presenter

import com.google.firebase.database.*

open class FbDatabasePresenter(_uid: String?){
    var firebaseDB:DatabaseReference = FirebaseDatabase.getInstance().reference.child(_uid)
}
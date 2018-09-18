package ms.imagine.foodiemate.presenters

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

open class FbDatabasePresenter(_uid: String?){
    var fbInstance:FirebaseDatabase = FirebaseDatabase.getInstance()
    var firebaseDB:DatabaseReference = fbInstance.reference.child(_uid)

}
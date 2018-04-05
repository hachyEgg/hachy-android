package ms.imagine.foodiemate.Presenter

import android.content.Context
import com.google.firebase.database.*

open class FbDatabasePresenter{
    var firebaseDB: DatabaseReference  //usernode here

    constructor(cContext: Context, _uid:String?){
        //callback = cCallback
        firebaseDB = FirebaseDatabase.getInstance().reference.child(_uid)
    }
}
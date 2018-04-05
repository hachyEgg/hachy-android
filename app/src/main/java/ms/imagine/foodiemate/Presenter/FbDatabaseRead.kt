package ms.imagine.foodiemate.Presenter

import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.views.DbReadCallBacks

class FbDatabaseRead: FbDatabasePresenter{
    lateinit var callback: DbReadCallBacks

    constructor(cContext: Context, _uid:String?, callBacks: DbReadCallBacks) :
            super(cContext, _uid) {
        callback = callBacks
        firebaseDB.addValueEventListener(postListener)
    }

    var postListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            dataSnapshot.children.forEach(fun(child){
                val egg = Egg(child.child("eggTag").getValue().toString(),
                        child.child("timestamp").getValue().toString().toLong(),
                        child.child("status").getValue().toString().toInt(),
                        child.child("imgURL").getValue().toString())
                Log.w("postegg", egg.toString())
                callback.retrieveEgg(child.key, egg)
            })
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            callback.retrieveEggError(databaseError.toException());
            Log.w("post", "loadPost:onCancelled", databaseError.toException())
        }
    }
}
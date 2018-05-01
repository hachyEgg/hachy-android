package ms.imagine.foodiemate.Presenter

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ms.imagine.foodiemate.callbacks.DbReadCallBacks
import ms.imagine.foodiemate.data.Eggs

class FbDatabaseRead: FbDatabasePresenter{
    lateinit var callback: DbReadCallBacks


    constructor(_uid:String?, callBacks: DbReadCallBacks) :
            super( _uid) {
        callback = callBacks
        firebaseDB.addValueEventListener(postListener)

    }

    var postListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            dataSnapshot.children.forEach(fun(child){
                val egg = Eggs(child.child("eggTag").value.toString(),
                        child.child("timestamp").value.toString().toLong(),
                        child.child("status").value.toString().toInt(),
                        child.child("imgURL").value.toString())
                egg.isnewEgg = false
                callback.retrieveEgg(child.key, egg)
            })
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            callback.retrieveEggError()
            Log.w("post", "loadPost:onCancelled", databaseError.toException())
        }
    }
}
package ms.imagine.foodiemate.Presenter

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ms.imagine.foodiemate.callbacks.DbReadCallBacks
import ms.imagine.foodiemate.data.Egg
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
            dataSnapshot.children.forEach(fun(c){

                val egg = Eggs(c.child("eggTag").value.toString(),
                        c.child("timestamp").value.toString().toLong(),
                        c.child("status").value.toString().toInt(),
                        c.child("imgURL").value.toString())

                // for non legacy eggs
                if (c.hasChild("egglist")){
                    c.child("egglist").children.forEach(fun(l){
                        egg.insertSnap( Egg(
                                l.child("imgURL").value.toString(),
                                l.child("status").value.toString().toInt(),
                                l.child("timestamp").value.toString().toLong()
                        ))
                    })
                }
                egg.isnewEgg = false
                callback.retrieveEgg(c.key, egg)
            })
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            callback.retrieveEggError()
            Log.w("post", "loadPost:onCancelled", databaseError.toException())
        }
    }
}
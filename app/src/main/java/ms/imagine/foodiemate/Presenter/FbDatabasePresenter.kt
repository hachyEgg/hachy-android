package ms.imagine.foodiemate.Presenter

import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import ms.imagine.foodiemate.views.IMainView
import ms.imagine.foodiemate.data.Egg

class FbDatabasePresenter{
    lateinit var mContext: Context;
    lateinit var mainview: IMainView;
    lateinit var firebaseDB: DatabaseReference;  //usernode here


    constructor(context: Context, view: IMainView, _uid:String?){
        mainview = view;
        mContext = context
        firebaseDB = FirebaseDatabase.getInstance().reference.child(_uid);
        firebaseDB.addValueEventListener(postListener)
    }

    fun writeEgg(egg: Egg){
        var map = HashMap<String, String>()
        map.put("eggTag", egg.eggtag);
        map.put("timestamp", egg.timestamp);
        map.put("status", egg.status);

        var leKey = firebaseDB.push().key
        firebaseDB.child(leKey).updateChildren(map as Map<String, Any>?);
    }

    var postListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            dataSnapshot.children.forEach(fun(child){
                val egg = Egg(child.child("eggTag").getValue().toString(),
                        child.child("timestamp").getValue().toString(),
                        child.child("status").getValue().toString())
                Log.w("postegg", egg.toString())
                mainview.retrieveEgg(child.key, egg)
            })
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            mainview.retrieveEggError(databaseError.toException());
            Log.w("post", "loadPost:onCancelled", databaseError.toException())
        }
    }








}
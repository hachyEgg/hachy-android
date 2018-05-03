package ms.imagine.foodiemate.Presenter

import ms.imagine.foodiemate.callbacks.DbWriteCallBacks
import ms.imagine.foodiemate.data.Eggs

class FbDatabaseWrite: FbDatabasePresenter{
    var callback: DbWriteCallBacks

    constructor(_uid:String?, callBacks: DbWriteCallBacks) :
            super(_uid) {
        callback = callBacks
    }

    fun writeEgg(eggs: Eggs){
        var map = HashMap<String, Any>()
        map.put("eggTag", eggs.eggtag)
        map.put("timestamp", eggs.timestamp)
        map.put("status", eggs.status)
        map.put("imgURL", eggs.remoteImgURL)

        if (!eggs.isLegacyEgg()){
            map.put("egglist", eggs.egg)
        }

        var leKey = firebaseDB.push().key
        firebaseDB.child(leKey).updateChildren(map as Map<String, Any>?)
                .addOnCompleteListener { callback.hashCode() }
                .addOnFailureListener {  }
    }
}
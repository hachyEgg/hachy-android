package ms.imagine.foodiemate.Presenter

import ms.imagine.foodiemate.callbacks.DbWriteCallBacks
import ms.imagine.foodiemate.data.Egg

class FbDatabaseWrite: FbDatabasePresenter{
    var callback: DbWriteCallBacks

    constructor(_uid:String?, callBacks: DbWriteCallBacks) :
            super(_uid) {
        callback = callBacks
    }

    fun writeEgg(egg: Egg){
        var map = HashMap<String, Any>()
        map.put("eggTag", egg.eggtag)
        map.put("timestamp", egg.timestamp)
        map.put("status", egg.status)
        map.put("imgURL", egg.remoteImgURL)

        var leKey = firebaseDB.push().key
        firebaseDB.child(leKey).updateChildren(map as Map<String, Any>?)
                .addOnCompleteListener { callback.hashCode() }
                .addOnFailureListener {  }
    }
}
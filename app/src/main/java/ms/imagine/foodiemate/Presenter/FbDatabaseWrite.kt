package ms.imagine.foodiemate.Presenter

import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.callbacks.DbReadCallBacks

class FbDatabaseWrite: FbDatabasePresenter{
    var callback: DbReadCallBacks

    constructor(_uid:String?, callBacks: DbReadCallBacks) :
            super(_uid) {
        callback = callBacks
    }

    fun writeEgg(egg: Egg){
        var map = HashMap<String, Any>()
        map.put("eggTag", egg.eggtag)
        map.put("timestamp", egg.timestamp)
        map.put("status", egg.status)
        map.put("imgURL", egg.imgURL)

        var leKey = firebaseDB.push().key
        firebaseDB.child(leKey).updateChildren(map as Map<String, Any>?);
    }
}
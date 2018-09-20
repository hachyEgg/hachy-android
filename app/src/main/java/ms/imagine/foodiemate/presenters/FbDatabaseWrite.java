package ms.imagine.foodiemate.presenters;

import android.util.Log;
import ms.imagine.foodiemate.callbacks.DbWriteCallBacks;
import ms.imagine.foodiemate.data.Eggs;

import java.util.HashMap;
import java.util.Map;

class FbDatabaseWrite extends FbDatabasePresenter{
    private DbWriteCallBacks callback;

    FbDatabaseWrite(String _uid, DbWriteCallBacks callBacks) {
        super(_uid);
        callback = callBacks;
    }

    public void writeEgg(Eggs eggs){
        Map<String, Object> map = new HashMap<>();
        map.put("eggTag", eggs.getEggtag());
        map.put("timestamp", eggs.getTimestamp());
        map.put("status", eggs.getStatus());
        map.put("imgURL", eggs.getRemoteImgURL());

        if (!eggs.isLegacyEgg()){
            map.put("egglist", eggs.getEgg());
        }

        String leKey = firebaseDB.push().getKey();
        firebaseDB.child(leKey).updateChildren(map)
        .addOnCompleteListener((s)->callback.hashCode())
                .addOnFailureListener(e -> {
                    Log.d("", "", e);});
    }
}
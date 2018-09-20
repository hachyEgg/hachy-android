package ms.imagine.foodiemate.presenters;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import ms.imagine.foodiemate.callbacks.DbReadCallBacks;
import ms.imagine.foodiemate.data.Egg;
import ms.imagine.foodiemate.data.Eggs;

public class FbDatabaseRead extends FbDatabasePresenter {
    DbReadCallBacks callback;


    public FbDatabaseRead(String _uid, DbReadCallBacks callBacks) {
        super(_uid);
        callback = callBacks;
        firebaseDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    Eggs egg = new Eggs(c.child("eggTag").getValue().toString(),
                            Long.parseLong( String.valueOf( c.child("timestamp").getValue())),
                            Integer.parseInt (String.valueOf (c.child("status").getValue())),
                            c.child("imgURL").getValue().toString());

                    // for non legacy eggs
                    if (c.hasChild("egglist")) {
                        for ( DataSnapshot l : c.child("egglist").getChildren()) {
                            egg.insertSnap(new Egg(
                                    l.child("imgURL").getValue().toString(),
                                    Integer.parseInt( String.valueOf( l.child("status").getValue().toString())),
                                    Long.parseLong( String.valueOf( l.child("timestamp").getValue().toString()))
                            ));
                        }

                    }
                    egg.setIsnewEgg(false);
                callback.retrieveEgg(c.getKey(), egg);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.retrieveEggError();
                Log.w("post", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}




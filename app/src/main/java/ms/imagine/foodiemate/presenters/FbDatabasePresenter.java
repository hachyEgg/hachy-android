package ms.imagine.foodiemate.presenters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FbDatabasePresenter {
    protected FirebaseDatabase fbInstance;
    protected DatabaseReference firebaseDB;

    FbDatabasePresenter(String _uid) {
        fbInstance = FirebaseDatabase.getInstance();
        firebaseDB = fbInstance.getReference().child(_uid);
    }
}
package ms.imagine.foodiemate.presenters;

import android.util.Log;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


class FbAuthStatePresenter {
    FirebaseAuth mAuth;  //usernode here


    public FbAuthStatePresenter(){
        mAuth = FirebaseAuth.getInstance();
    }

    FirebaseUser userState() {
        return mAuth.getCurrentUser();
    }

    void signOut(){
        mAuth.signOut();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Log.w("SIGNs", "not null MAuth");
    }
}
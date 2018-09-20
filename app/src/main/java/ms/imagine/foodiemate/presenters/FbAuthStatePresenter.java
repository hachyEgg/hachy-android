package ms.imagine.foodiemate.presenters;

import android.util.Log;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FbAuthStatePresenter {
    FirebaseAuth mAuth;  //usernode here


    public FbAuthStatePresenter(){
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser userState() {
        return mAuth.getCurrentUser();
    }

    public void signOut(){
        mAuth.signOut();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Log.w("SIGNs", "not null MAuth");
    }
}
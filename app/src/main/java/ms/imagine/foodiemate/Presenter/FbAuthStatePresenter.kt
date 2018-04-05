package ms.imagine.foodiemate.Presenter

import android.util.Log
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class FbAuthStatePresenter {
    var mAuth: FirebaseAuth;  //usernode here


    constructor(){
        mAuth = FirebaseAuth.getInstance()
    }

    fun userState(): FirebaseUser? {
        return mAuth.currentUser
    }

    fun signOut(){
        mAuth.signOut()
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()
        Log.w("SIGNs", "not null MAuth")
    }
}
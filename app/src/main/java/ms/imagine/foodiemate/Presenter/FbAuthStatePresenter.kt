package ms.imagine.foodiemate.Presenter

import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ms.imagine.foodiemate.activity.FacebookLoginActivity
import ms.imagine.foodiemate.activity.MainActivity
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.views.IAuthView
import ms.imagine.foodiemate.views.IMainView


class FbAuthStatePresenter: FirebaseAuth.AuthStateListener{
    var mContext: Context;
    var mainview: IMainView;
    var mAuth: FirebaseAuth;  //usernode here


    constructor(context: Context, view: IMainView){
        mainview = view;
        mContext = context
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
        mainview.signOut()
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        if (userState()==null){
            mainview.signOut();
        }
    }


}
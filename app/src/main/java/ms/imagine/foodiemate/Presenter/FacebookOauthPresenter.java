package ms.imagine.foodiemate.Presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import ms.imagine.foodiemate.FacebookLoginActivity;
import ms.imagine.foodiemate.views.ILoginView;

/**
 * Created by eugen on 3/27/2018.
 */

public class FacebookOauthPresenter implements IFacebookOauthPresenter{
    ILoginView mILoginView;
    Context mContext;

    FacebookOauthPresenter(Context context, ILoginView view){
        mILoginView = view;
        mContext = context;
    }


}

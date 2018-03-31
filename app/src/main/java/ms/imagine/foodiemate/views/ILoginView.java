package ms.imagine.foodiemate.views;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by eugen on 3/26/2018.
 */

public interface ILoginView {
    String getTag();
    void loginSuccess();
    void loginFailed();
    void onLoginStatusChanged(FirebaseUser user);
}

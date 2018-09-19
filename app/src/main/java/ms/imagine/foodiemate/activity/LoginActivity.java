/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ms.imagine.foodiemate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import ms.imagine.foodiemate.R;


/**
 * Demonstrate Firebase Authentication using a Facebook access token.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private Button facebookButton;
    private Button deviceButton;
    private static final String TAG = "FacebookLogin";
    ProgressBar pb1;

    // mAuth declared in BaseActivity
    private CallbackManager mCallbackManager;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        facebookButton = findViewById(R.id.btn_sub_facebook);
        deviceButton = findViewById(R.id.btn_sub_device);
        pb1 = findViewById(R.id.pb1);

        facebookButton.setOnClickListener(this);
        deviceButton.setOnClickListener(this);

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        com.facebook.login.widget.LoginButton loginButton = findViewById(R.id.button_facebook_login);


        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override public void onSuccess(LoginResult loginResult) {
                Log.d(TAG , String.format("facebook:onSuccess: %s", loginResult));
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                onLoginStatusChanged();
            }

            @Override public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError %s", error);
                onLoginStatusChanged();
            }
        });
    }

    // Check if User is logged in on start
    @Override public void onStart() {
        super.onStart();
        Log.w(TAG, "entered on startup");
        onLoginStatusChanged();
    }

    // Received Result from FB
    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleAnynomousSignIn() {
        pb1.setVisibility(View.VISIBLE);
        mAuth.signInAnonymously().addOnCompleteListener( (task) -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInAnonymously:success");
                onLoginStatusChanged();
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInAnonymously:failure", task.getException());
                toast(getString(R.string.auth_failed));
                onLoginStatusChanged();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG , String.format("handleFacebookAccessToken: %s", token));
        pb1.setVisibility(View.VISIBLE);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG ,"signInWithCredential:success");
                onLoginStatusChanged();
            } else {
                // If sign in fails, display a message to the user.
                Log.d(TAG , "signInWithCredential:failure", task.getException());
                toast(getString(R.string.auth_failed));
                onLoginStatusChanged();
            }
            pb1.setVisibility(View.GONE);
        });
    }


    private void onLoginStatusChanged() {
        pb1.setVisibility(View.GONE);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra(getString(R.string.tag_user), user);
            finish();
            startActivity(i);
        } else {
            toast(getString(R.string.log_out));
            // user would be expected to know that they are logged out
        }
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sub_facebook:
                facebookButton.performClick();
                break;
            case R.id.btn_sub_device:
                handleAnynomousSignIn();
                break;
        }
    }
}

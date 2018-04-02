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

package ms.imagine.foodiemate.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

import ms.imagine.foodiemate.R

/**
 * Demonstrate Firebase Authentication using a Facebook access token.
 */
class FacebookLoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mBtnDevice: Button
    private lateinit var mBtnFacebook: Button
    private lateinit var loginButton: LoginButton


    // mAuth declared in BaseActivity
    private var mCallbackManager: CallbackManager? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_facebook)

        mBtnDevice = findViewById(R.id.btn_sub_device)
        mBtnFacebook = findViewById(R.id.btn_sub_facebook)
        mBtnFacebook.setOnClickListener(this)
        mBtnDevice.setOnClickListener(this)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create()
        loginButton = findViewById(R.id.button_facebook_login)
        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult)
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                onLoginStatusChanged()
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                onLoginStatusChanged()
            }
        })
    }

    // Check if User is logged in on start
    public override fun onStart() {
        super.onStart()
        Log.w("eugwarn", "entered on startup")
        onLoginStatusChanged()
    }

    // call facebook activity for signin operation
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleAnynomousSignIn() {
        mAuth!!.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInAnonymously:success")
                        onLoginStatusChanged()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInAnonymously:failure", task.exception)
                        Toast.makeText(this@FacebookLoginActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        onLoginStatusChanged()
                    }
                }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + token)
        showProgressDialog()

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        onLoginStatusChanged()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(this@FacebookLoginActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        onLoginStatusChanged()
                    }
                    hideProgressDialog()
                }
    }


    private fun onLoginStatusChanged() {
        val user = mAuth!!.currentUser
        if (user != null) {
            val i = Intent(this@FacebookLoginActivity, MainActivity::class.java)
            i.putExtra("user", user)
            finish()
            startActivity(i)

        } else {
            Toast.makeText(this, "Oops, you are logged out", Toast.LENGTH_LONG)
        }
    }

    // This is how you sign out
    // signout not used in this activity
    fun signOut() {
        mAuth!!.signOut()
        LoginManager.getInstance().logOut()
        onLoginStatusChanged()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_sub_facebook -> loginButton.performClick()
            R.id.btn_sub_device -> handleAnynomousSignIn()
            else -> {
            }
        }

    }

    companion object {
        private val TAG = "FacebookLogin"
    }


}

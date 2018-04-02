package ms.imagine.foodiemate.views

import com.google.firebase.auth.FirebaseUser

/**
 * Created by eugen on 3/26/2018.
 */

interface ILoginView {
    val tag: String
    fun loginSuccess()
    fun loginFailed()
    fun onLoginStatusChanged(user: FirebaseUser)
}

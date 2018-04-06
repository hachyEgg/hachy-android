package ms.imagine.foodiemate.activity

/**
 * Created by eugen on 3/26/2018.
 */

import android.content.Context
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import ms.imagine.foodiemate.R
import android.widget.RelativeLayout
import android.widget.ProgressBar



open class BaseActivity : AppCompatActivity() {

    @VisibleForTesting
    var progressBar: ProgressBar? = null

    fun showProgressDialog() {
        if (progressBar == null) {
            progressBar = findViewById(R.id.pb1)
        }
        progressBar!!.visibility = View.VISIBLE  //To show ProgressBar
    }

    fun toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun hideProgressDialog() {
        if (progressBar != null) {
            progressBar!!.visibility = View.INVISIBLE     // To Hide ProgressBar
            progressBar!!.visibility = View.GONE     // To Hide ProgressBar
        }
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }


}
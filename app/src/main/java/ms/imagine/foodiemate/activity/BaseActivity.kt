package ms.imagine.foodiemate.activity

/**
 * Created by eugen on 3/26/2018.
 */

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import ms.imagine.foodiemate.utils.BgData


open class BaseActivity : AppCompatActivity() {
    internal lateinit var bG:BgData
    fun toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun setBg(lol:Context){ bG= BgData(lol)}

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
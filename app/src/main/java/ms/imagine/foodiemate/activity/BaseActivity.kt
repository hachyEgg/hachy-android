package ms.imagine.foodiemate.activity

/**
 * Created by eugen on 3/26/2018.
 */

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.utils.BgData


open class BaseActivity : AppCompatActivity() {
    internal lateinit var bG:BgData
    fun toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
package ms.imagine.foodiemate.activity

/**
 * Created by eugen on 3/26/2018.
 */

import android.content.Intent
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.Toast
import ms.imagine.foodiemate.R


open class BaseActivity : AppCompatActivity() {
    fun toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun startImagePicActivity(){
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, MainActivity.SELECT_PIC_LOCAL)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
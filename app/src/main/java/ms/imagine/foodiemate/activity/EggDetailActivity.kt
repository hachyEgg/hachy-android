package ms.imagine.foodiemate.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView


import ms.imagine.foodiemate.R

class EggDetailActivity : AppCompatActivity() {
    lateinit var imgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_egg_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //var bitmap = intent.extras.get("IMG") as Bitmap
        //imgView = findViewById(R.id.captured_image_detail)
        //imgView.setImageBitmap(bitmap);

        val fab = findViewById<FloatingActionButton>(R.id.eggdetail_fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}

package ms.imagine.foodiemate.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import ms.imagine.foodiemate.R

import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.utils.BgData

class DetailActivity : BaseActivity() {

    //Following field for creating Egg

    //Following field for viewing an egg




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        var a = intent.extras.get("Egg") as Egg
        toast(a.toString())

    }

    fun determineEgg() {

    }

}

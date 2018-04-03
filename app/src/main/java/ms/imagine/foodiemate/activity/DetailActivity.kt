package ms.imagine.foodiemate.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.data.Egg

// There has ato be an egg being passsed into this activity for it to function normally,
// This could be a sudo Egg, but an egg has to present
// This Activity display Egg Information
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


        var egg = intent.extras.get("Egg") as Egg
        toast(egg.toString())

    }

    fun determineEgg() {

    }

}

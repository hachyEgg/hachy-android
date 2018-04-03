package ms.imagine.foodiemate.activity

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.activity.MainActivity.Companion.NULL
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.utils.BgData

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


        var egg = intent.extras.get("Egg") as Egg
        titleBox.text = egg.eggtag
        time.text = egg.displayTime()
        status.text = egg.status;
        key_button.text = "Remove Egg"



        var newEgg = intent.extras.get("isNewEgg") as Boolean?
        if (newEgg != null && newEgg){
            determineEgg()
        }
    }

    fun determineEgg() {
        imgView.setImageURI(Uri.parse(BgData.retrieve(this, MainActivity.TAKE_PIC_FINISHED, NULL)))
    }

}

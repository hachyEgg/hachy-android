package ms.imagine.foodiemate.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.Presenter.*
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.views.IDetailedView


class DetailActivity : BaseActivity(), IDetailedView {
    private lateinit var storagePresenter: FbStorageWrite
    private lateinit var egg: Egg
    private lateinit var eggDeterminator: EggDeterminator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        println("getEggbefore")


        egg = intent.extras.get("Egg") as Egg
        titleBox.text = egg.eggtag
        time.text = egg.displayTime()


        eggDeterminator = EggDeterminator(this, egg)

        if (egg.isnewEgg){
            showProgress(true)
            var uri = (egg.localImgUri)

            //here we are getting only the last segment of Uri, we dont want the full url as
            // for the sake of database abstraction

            egg.remoteImgURL = uri.lastPathSegment.toString()
            imgView.setImageURI(uri)
            eggDeterminator.upload()
        } else {
            status.text = egg.displayStatus()
            eggDeterminator.download(this, imgView)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home-> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun updateStatus(state: String){
        println("updateStatus"+Thread.currentThread().name)
        status.text = state
    }

    override fun toast(st: String) {
        println("toast"+Thread.currentThread().name)
        super.toast(st)
    }

    override fun showProgress(show: Boolean) {
        println("showProgress"+Thread.currentThread().name)
        when(show){
            true-> pb1.visibility = View.VISIBLE
            false -> pb1.visibility = View.GONE
        }
    }

}

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
        toolbar.getNavigationIcon()?.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        eggDeterminator = EggDeterminator(this, egg)

        egg = intent.extras.get("Egg") as Egg
        titleBox.text = egg.eggtag
        time.text = egg.displayTime()

        if (egg.isnewEgg){
            showProgress(true)
            var uri = (egg.localImgUri)
            egg.remoteImgURL = uri.lastPathSegment.toString()
            imgView.setImageURI(uri)
            eggDeterminator.upload()
        } else {
            status.text = egg.displayStatus()
            eggDeterminator.download(this, imgView)
            storagePresenter.downloadImage(this, egg.remoteImgURL, imgView)
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
        status.text = state
    }

    override fun toast(st: String) {
        toast(st)
    }

    override fun showProgress(show: Boolean) {
        when(show){
            true-> pb1.visibility = View.VISIBLE
            false -> pb1.visibility = View.GONE
        }
    }
}

package ms.imagine.foodiemate.activity

import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.Presenter.AzurePresenter
import ms.imagine.foodiemate.Presenter.FbAuthStatePresenter
import ms.imagine.foodiemate.Presenter.FbDatabaseWrite
import ms.imagine.foodiemate.Presenter.FbStorageWrite
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.activity.MainActivity.Companion.NULL
import ms.imagine.foodiemate.api.AzureClient
import ms.imagine.foodiemate.api.AzurePrediction
import ms.imagine.foodiemate.api.Prediction
import ms.imagine.foodiemate.callbacks.AzureCallBacks
import ms.imagine.foodiemate.callbacks.DbWriteCallBacks
import ms.imagine.foodiemate.callbacks.StWriteCallBacks
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.data.EggStages
import ms.imagine.foodiemate.utils.Eulog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Note: must have an Egg for this activity (hint: create a fake egg)
class DetailActivity : BaseActivity(), StWriteCallBacks, DbWriteCallBacks, AzureCallBacks {
    private lateinit var storagePresenter: FbStorageWrite
    private lateinit var databaseWrite: FbDatabaseWrite
    private lateinit var azure: AzurePresenter
    private lateinit var egg: Egg
    private lateinit var uriImg: String
    private lateinit var azureClient: AzureClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.getNavigationIcon()?.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        storagePresenter = FbStorageWrite( this)
        databaseWrite = FbDatabaseWrite(FbAuthStatePresenter().userState()!!.uid, this)
        azure = AzurePresenter(this)

        egg = intent.extras.get("Egg") as Egg
        titleBox.text = egg.eggtag
        time.text = egg.displayTime()


        var newEgg = intent.extras.get("isNewEgg") as Boolean?
        if (newEgg != null && newEgg){
            determineEgg()
            status.text=""
        } else {
            status.text = egg.displayStatus()
            storagePresenter.downloadImage(this, egg.imgURL, imgView)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun determineEgg() {
        setBg(this)
        var uri = Uri.parse(bG.retrieve(this, MainActivity.TAKE_PIC_FINISHED, NULL))
        uriImg = uri.lastPathSegment.toString()
        imgView.setImageURI(uri)
        storagePresenter.uploadImage(uri)
        pb1.visibility = View.VISIBLE
    }


    //Storage Upload good or bad
    override fun uploadFailed() {
        pb1.visibility = View.GONE
        toast("failed")
    }

    override fun uploadSuccess(uri: Uri?) {
        Log.w("EUGWARN_CAM", uri.toString())

        val remote_url = uri.toString()
        toast("Image Successfully uploaded")
        egg.imgURL = uriImg

        //Next Azure Analysis
        azure.postImageFromUrl(remote_url)
    }

    // DBWrite Good or bad
    override fun onDbWriteSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onDbWriteFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // Azure stuff
    override fun onAzureSuccess(prediction: List<Prediction>) {
        val egS = EggStages(prediction)

        egg.status = egS.waEgg()

        //toast(egg.displayStatus())
        val state = egg.displayStatus()
        println("status: " + state)

        databaseWrite.writeEgg(egg)
        println("After writeEgg")

        try {
            runOnUiThread({
                pb1.visibility = View.GONE
                status.text = state
            })
        } catch (e:Exception){

        }
    }

    override fun onAzureFailure(str: String) {
        pb1.visibility = View.GONE
    }
}

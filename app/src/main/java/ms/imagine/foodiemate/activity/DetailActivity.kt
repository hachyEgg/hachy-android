package ms.imagine.foodiemate.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.activity.MainActivity.Companion.NULL
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.utils.BgData
import ms.imagine.foodiemate.Presenter.AzurePresenter
import ms.imagine.foodiemate.Presenter.FbAuthStatePresenter
import ms.imagine.foodiemate.Presenter.FbDatabaseWrite
import ms.imagine.foodiemate.Presenter.FbStorageWrite
import ms.imagine.foodiemate.callbacks.AzureCallBacks
import ms.imagine.foodiemate.callbacks.DbWriteCallBacks
import ms.imagine.foodiemate.callbacks.StWriteCallBacks
import ms.imagine.foodiemate.data.EggStagePossibility


// Note: must have an Egg for this activity (hint: create a fake egg)
class DetailActivity : BaseActivity(), StWriteCallBacks, DbWriteCallBacks, AzureCallBacks {
    private lateinit var storagePresenter: FbStorageWrite
    private lateinit var databaseWrite: FbDatabaseWrite;
    private lateinit var azure: AzurePresenter;
    private lateinit var egg: Egg
    private lateinit var uriImg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        storagePresenter = FbStorageWrite( this)
        databaseWrite = FbDatabaseWrite(FbAuthStatePresenter().userState()!!.uid, this)
        azure = AzurePresenter(this)

        egg = intent.extras.get("Egg") as Egg
        titleBox.text = egg.eggtag
        time.text = egg.displayTime()
        status.text = egg.displayStatus()

        var newEgg = intent.extras.get("isNewEgg") as Boolean?
        if (newEgg != null && newEgg){
            determineEgg()
        } else {
            storagePresenter.downloadImage(this, egg.imgURL, imgView)
        }
    }
    private fun eggHandling() {

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
        var uri = Uri.parse(BgData.retrieve(this, MainActivity.TAKE_PIC_FINISHED, NULL))
        uriImg = uri.lastPathSegment.toString()
        imgView.setImageURI(uri)
        storagePresenter.uploadImage(uri)
        showProgressDialog()
    }


    //Storage Upload good or bad
    override fun uploadFailed() {
        hideProgressDialog()
        toast("failed")
    }

    override fun uploadSuccess(uri: Uri?) {
        hideProgressDialog() // cannot stop here, still gotta do recognition
        Log.w("EUGWARN_CAM", uri.toString())

        val remote_url = uri.toString()
        toast(""+remote_url)
        egg.imgURL = uriImg



        azure.dispatch(remote_url);
        //write to DB afterwards  even more ...
    }

    // DBWrite Good or bad
    override fun onDbWriteSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onDbWriteFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // Azure stuff
    override fun onAzureSuccess(str: String) {
        // hideProgressDialog()
        println(str)
        databaseWrite.writeEgg(egg)
    }

    override fun onAzureFailure() {
        // hideProgressDialog()
    }
}

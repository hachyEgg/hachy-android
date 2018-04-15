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
class DetailActivity : BaseActivity(), StWriteCallBacks, DbWriteCallBacks, AzureCallBacks, Callback<AzurePrediction> {
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


        val builder = Retrofit.Builder()
                .baseUrl("https://southcentralus.api.cognitive.microsoft.com/" +
                        "customvision/v1.1/Prediction/bf3f0d19-cb5b-4dee-95c7-23b530221fa4/")
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        azureClient = retrofit.create(AzureClient::class.java)




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
        // pb1.visibility = View.GONE // cannot stop here, still gotta do recognition
        Log.w("EUGWARN_CAM", uri.toString())

        val remote_url = uri.toString()
        toast("Image Successfully uploaded")
        //toast(""+remote_url)
        egg.imgURL = uriImg


        try {
            val map = HashMap<String, String>()
            map.put("url", remote_url)
            azureClient.postImageFromUrl(map).enqueue(this)
            Eulog.w("afterEnquire")
            println("Eulog.w(\"afterEnquire\")")
        } catch (e:Exception){
            e.printStackTrace();
        }

        //azure.dispatch(remote_url)
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
        println("beforeHide")
        //pb1.visibility = View.GONE
        println("backInCallBacc")

        val egS = EggStages(str)
        println("After EggStageCration")

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



    override fun onAzureFailure() {
        pb1.visibility = View.GONE
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
    override fun onFailure(call: Call<AzurePrediction>?, t: Throwable?) {
        println("error")
        println (t.toString())
        println (call)
    }

    /**
     * Invoked for a received HTTP response.
     *
     *
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call [Response.isSuccessful] to determine if the response indicates success.
     */
    override fun onResponse(call: Call<AzurePrediction>?, response: Response<AzurePrediction>?) {
        pb1.visibility = View.GONE
        var azurePrediction: AzurePrediction? = response?.body()
        println("PDC" + response.toString() + azurePrediction)
    }
}

package ms.imagine.foodiemate.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.activity.MainActivity.Companion.NULL
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.utils.BgData
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import ms.imagine.foodiemate.Presenter.FbStorageWrite
import ms.imagine.foodiemate.callbacks.StWriteCallBacks


// Note: must have an Egg for this activity (hint: create a fake egg)
class DetailActivity : BaseActivity(), StWriteCallBacks {
    internal lateinit var storagePresenter: FbStorageWrite
    internal lateinit var egg: Egg
    internal lateinit var uriImg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        storagePresenter = FbStorageWrite( this)

        egg = intent.extras.get("Egg") as Egg
        titleBox.text = egg.eggtag
        time.text = egg.displayTime()
        status.text = egg.displayStatus()
        toast(egg.imgURL)



        var newEgg = intent.extras.get("isNewEgg") as Boolean?
        if (newEgg != null && newEgg){
            determineEgg()
        } else {

            Glide.with(this /* context */)
                    .using<StorageReference>(FirebaseImageLoader())
                    .load(storagePresenter.imagesRef.child(egg.imgURL))
                    .into(imgView)
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

    override fun uploadFailed() {
        hideProgressDialog()
        toast("failed")
    }

    override fun uploadSuccess(uri: Uri?) {
        hideProgressDialog()
        Log.w("EUGWARN_CAM", uri.toString());
        toast("win @"+uri.toString())
        egg.imgURL = uriImg


        //determine egg before write egg

        //write some eggs here
        writeEgg(egg);
        //fbDatabasePresenter.writeEgg(egg)
    }

    fun writeEgg(egg: Egg){
        var map = HashMap<String, Any>()
        map.put("eggTag", egg.eggtag);
        map.put("timestamp", egg.timestamp);
        map.put("status", egg.status);
        map.put("imgURL", egg.imgURL);

        var firebaseDB = FirebaseDatabase.getInstance().reference.child(FirebaseAuth.getInstance().currentUser!!.uid)
        var leKey = firebaseDB.push().key
        firebaseDB.child(leKey).updateChildren(map as Map<String, Any>?);
    }

    //This function present the info to Azure for Recognition
    fun azureEggRecon(url: String){
        // val textView = null;        // destination of data

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.google.com"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    toast("Response is: ${response.substring(0, 500)}")
                },
                Response.ErrorListener { toast ("That didn't work!") })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

}

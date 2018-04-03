package ms.imagine.foodiemate.activity

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseException
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.Presenter.FbAuthStatePresenter
import ms.imagine.foodiemate.Presenter.FbDatabasePresenter
import ms.imagine.foodiemate.Presenter.StorageUploadPresenter
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.activity.MainActivity.Companion.NULL
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.utils.BgData
import ms.imagine.foodiemate.views.IAuthView
import ms.imagine.foodiemate.views.IDetailView
import ms.imagine.foodiemate.views.IFbDataBase
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference


// There has ato be an egg being passsed into this activity for it to function normally,
// This could be a sudo Egg, but an egg has to present
// This Activity display Egg Information
class DetailActivity : BaseActivity(), IDetailView, IFbDataBase, IAuthView {


    //Following field for creating Egg
    //Following field for viewing an egg
    internal lateinit var storageUploadPresenter: StorageUploadPresenter
    internal lateinit var fbAuth: FbAuthStatePresenter
    internal lateinit var fbDatabasePresenter: FbDatabasePresenter
    internal lateinit var egg: Egg
    internal lateinit var uriImg: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)


        egg = intent.extras.get("Egg") as Egg
        titleBox.text = egg.eggtag
        time.text = egg.displayTime()
        status.text = egg.status;
        key_button.text = "Remove Egg"
        toast(egg.imgURL)


        storageUploadPresenter = StorageUploadPresenter(this, this)




        var newEgg = intent.extras.get("isNewEgg") as Boolean?
        if (newEgg != null && newEgg){
            determineEgg()
        } else {
            Glide.with(this /* context */)
                    .using<StorageReference>(FirebaseImageLoader())
                    .load(storageUploadPresenter.imgReference().child(egg.imgURL))
                    .into(imgView)
        }
    }

    fun determineEgg() {

        var uri = Uri.parse(BgData.retrieve(this, MainActivity.TAKE_PIC_FINISHED, NULL))
        uriImg = uri.lastPathSegment.toString()
        imgView.setImageURI(uri)
        storageUploadPresenter.uploadImage(uri)
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


        //write some eggs here

        //fbDatabasePresenter.writeEgg(egg)
    }

    override fun retrieveEgg(key: String, egg: Egg) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun retrieveEggError(e: DatabaseException) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signOut() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

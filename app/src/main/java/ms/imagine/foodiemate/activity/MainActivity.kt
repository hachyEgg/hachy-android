package ms.imagine.foodiemate.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ms.imagine.foodiemate.Presenter.FbAuthStatePresenter
import ms.imagine.foodiemate.Presenter.FbDatabasePresenter
import ms.imagine.foodiemate.Presenter.FbDatabaseRead
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.adapter.ResViewAdapter
import ms.imagine.foodiemate.callbacks.DbReadCallBacks
import ms.imagine.foodiemate.data.Eggs
import ms.imagine.foodiemate.utils.Eulog
import java.net.URI


class MainActivity : BaseActivity(), DbReadCallBacks, ResViewAdapter.OnItemClicked {
    private lateinit var fbdatabase: FbDatabasePresenter
    private lateinit var fbAuthStatePresenter: FbAuthStatePresenter
    private lateinit var viewAdapter: ResViewAdapter
    private lateinit var eggIndex: HashSet<String>
    private lateinit var list: ArrayList<Eggs>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //fab icon
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            checkPermissionToProceed()
        }

        // FB connection Presenters:
        eggIndex = HashSet()
        fbAuthStatePresenter = FbAuthStatePresenter()
        fbdatabase = FbDatabaseRead(fbAuthStatePresenter.userState()!!.uid, this)

        //Logic
        if (fbAuthStatePresenter.userState() == null) finish()
        list = ArrayList()
        ResViewInit(list)
    }

    private fun openCamera(){
        val i = Intent(this@MainActivity, CameraActivity::class.java)
        startActivity(i)
    }

    fun checkPermissionToProceed() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    MY_PERMISSIONS_REQUEST_CAMERA)
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_STORAGE)
        } else {
            openCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkPermissionToProceed()
                } else {
                    toast(getString(R.string.plead_permission_camera))
                }
            }
            MY_PERMISSIONS_REQUEST_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkPermissionToProceed()
                } else {
                    toast(getString(R.string.plead_permission_storage))
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("OnREsult") // we should probably no do this
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == TAKE_PIC_CAMERA){
                val uri = data?.extras?.get(TAKE_PIC_FINISHED) as URI
                Eulog.w(uri.toString())
            } else if (requestCode == SELECT_PIC_LOCAL) {
                println("imgSelect_OK")
                val uri: Uri? = data?.data
                if (uri!=null ) {
                    val i = Intent(this@MainActivity, DetailActivity::class.java)
                    i.putExtra("Egg", Eggs("coo", System.currentTimeMillis(), 0,uri))
                    println("start")
                    startActivity(i)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun ResViewInit(coolDataHere: ArrayList<Eggs>){
        val viewManager = LinearLayoutManager(this)
        viewAdapter = ResViewAdapter(coolDataHere, this)
        viewAdapter.setOnClick(this)
        my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_signout -> {
                signOut()
                true
            }
            R.id.action_imageselect -> {
                startImagePicActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun signOut() {
        fbAuthStatePresenter.signOut()
        val i = Intent(this@MainActivity, LoginActivity::class.java)
        i.putExtra(TO_SIGN_OUT, true)
        finish()
        startActivity(i)
    }

    override fun retrieveEgg(key: String, eggs: Eggs){
        pb1.visibility = View.GONE
        if(eggIndex.add(key)){
            list.add(0,eggs)
            viewAdapter.notifyDataSetChanged()
        }
    }

    override fun retrieveEggError() {
        toast(getString(R.string.failed_retrieve_eggs))
    }

    fun showEggDetail(eggs: Eggs) {
        var i = Intent(this@MainActivity, DetailActivity::class.java)
        i.putExtra("Egg", eggs)
        startActivity(i)
    }

    override fun onItemClick(position: Int) {
        showEggDetail(list.get(position))
    }

    companion object {
        const val TO_SIGN_OUT = "sign_out"
        const val TAKE_PIC_CAMERA = 0x9
        const val SELECT_PIC_LOCAL = 0x7
        const val TAKE_PIC_FINISHED = "picTaken"
        const val NULL = "null_Found"
        const val MY_PERMISSIONS_REQUEST_CAMERA = 0x667
        const val MY_PERMISSIONS_REQUEST_STORAGE = 0x548
    }
}


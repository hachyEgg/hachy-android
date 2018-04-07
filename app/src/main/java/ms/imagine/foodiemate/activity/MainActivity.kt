package ms.imagine.foodiemate.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import ms.imagine.foodiemate.Presenter.FbAuthStatePresenter
import ms.imagine.foodiemate.Presenter.FbDatabasePresenter
import ms.imagine.foodiemate.Presenter.FbDatabaseRead
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.adapter.ResViewAdapter
import ms.imagine.foodiemate.callbacks.DbReadCallBacks
import ms.imagine.foodiemate.data.Egg
import java.net.URI


class MainActivity : BaseActivity(), DbReadCallBacks, ResViewAdapter.OnItemClicked {
    private lateinit var fbdatabase: FbDatabasePresenter
    private lateinit var fbAuthStatePresenter: FbAuthStatePresenter
    private lateinit var viewAdapter: ResViewAdapter
    private lateinit var eggIndex: HashSet<String>
    private lateinit var list: ArrayList<Egg>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //fab icon
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val i = Intent(this@MainActivity, CameraActivity::class.java)
            startActivity(i);
        }

        // FB connection Presenters:
        eggIndex = HashSet();
        fbAuthStatePresenter = FbAuthStatePresenter()
        fbdatabase = FbDatabaseRead(fbAuthStatePresenter.userState()!!.uid, this)

        //Logic
        if (fbAuthStatePresenter.userState() == null) finish();
        list = ArrayList();
        ResViewInit(list);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("OnREsult")
        if (requestCode == TAKE_PIC_CAMERA){
            if (resultCode == Activity.RESULT_OK){
                val uri = data?.extras?.get(TAKE_PIC_FINISHED) as URI;
                Log.w("EUGWARN_CAM", uri.toString())
            }
        } else if (requestCode == SELECT_PIC_LOCAL) {
            println("selectLocal")
            if (resultCode == Activity.RESULT_OK){
                println("imgSelect_OK")
                val uri = data?.data
                if (uri!=null ) {
                    println("uri.notnull")

                    // No need to zip hence ignored here
                    //var uris = Image.createImage(uri);
                    // got to detailed View Here
                    bG.write(TAKE_PIC_FINISHED, uri.toString())
                    val i = Intent(this@MainActivity, DetailActivity::class.java)
                    i.putExtra("isNewEgg", true)
                    i.putExtra("Egg", Egg("coo", System.currentTimeMillis(), 0))
                    startActivity(i)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun ResViewInit(coolDataHere: ArrayList<Egg>){
        val viewManager = LinearLayoutManager(this)
        viewAdapter = ResViewAdapter(coolDataHere, this)
        viewAdapter.setOnClick(this)
        my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.action_signout -> {
                fbAuthStatePresenter.signOut();
                signOut()
                return true
            }
            R.id.action_imageselect -> {
                val getIntent = Intent(Intent.ACTION_GET_CONTENT)
                getIntent.type = "image/*"

                val pickIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                pickIntent.type = "image/*"

                val chooserIntent = Intent.createChooser(getIntent, "Select Image")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

                startActivityForResult(chooserIntent, SELECT_PIC_LOCAL)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun signOut() {
        val i = Intent(this@MainActivity, FacebookLoginActivity::class.java)
        i.putExtra(TO_SIGN_OUT, true)
        finish()
        startActivity(i)
    }

    override fun retrieveEgg(key: String, egg: Egg){
        if(eggIndex.add(key)){
            list.add(0,egg)
            viewAdapter.notifyDataSetChanged()
        }
    }

    override fun retrieveEggError() {
        toast("failed to retrieve eggs")
    }

    fun showEggDetail(egg: Egg) {
        var i = Intent(this@MainActivity, DetailActivity::class.java)
        i.putExtra("Egg", egg);
        startActivity(i)
    }

    override fun onItemClick(position: Int) {
        // toast(""+position)
        showEggDetail(list.get(position));
    }

    companion object {
        const val TO_SIGN_OUT = "sign_out"
        const val TAKE_PIC_CAMERA = 0x9
        const val SELECT_PIC_LOCAL = 0x7
        const val TAKE_PIC_FINISHED = "picTaken"
        const val NULL = "null_Found"
    }
}


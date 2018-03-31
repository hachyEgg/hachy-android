package ms.imagine.foodiemate.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.adapter.ResViewAdapter
import ms.imagine.foodiemate.data.Egg

class MainActivity : BaseActivity(), FirebaseAuth.AuthStateListener {
    private lateinit var mDatabase: DatabaseReference
    internal lateinit var txt: TextView
    private lateinit var mAuth: FirebaseAuth

    //RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //List
    private lateinit var list: ArrayList<Egg>;

    override fun onCreate(savedInstanceState: Bundle?) {
        //View System set up
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt = findViewById(R.id.txt)

        //toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //fab icon
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val i = Intent(this@MainActivity, CameraActivity::class.java)
            list.add(0,Egg());
            recyclerView.adapter.notifyDataSetChanged();
            //startActivity(i)
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        //DB stuff
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference


        //Logic
        checkUser()
        list = ArrayList<Egg>();

        //Думмы Дата Думп

        ResViewInit(list);
    }

    private fun ResViewInit(coolDataHere: ArrayList<Egg>){
        viewManager = LinearLayoutManager(this)
        viewAdapter = ResViewAdapter(coolDataHere)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }


    private fun checkUser() {
        val b = intent.extras
        val user = b.get("user") as FirebaseUser?

        if (user == null) {
            txt.text = "null"
            finish()

        } else {
            txt.text = user.uid
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
                signOut()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun signOut() {
        mAuth.signOut()
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()
        Log.w("SIGNs", "not null MAuth")

        val i = Intent(this@MainActivity, FacebookLoginActivity::class.java)
        i.putExtra(TO_SIGN_OUT, true)
        finish()
        startActivity(i)
    }

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        val i = Intent(this@MainActivity, FacebookLoginActivity::class.java)
        i.putExtra(TO_SIGN_OUT, true)
        finish()
        startActivity(i)
    }

    companion object {
        const val TO_SIGN_OUT = "sign_out"
    }
}

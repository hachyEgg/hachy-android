package ms.imagine.foodiemate.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_detail.*
import ms.imagine.foodiemate.Presenter.*
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.adapter.EggsDetailAdapter
import ms.imagine.foodiemate.data.Eggs
import ms.imagine.foodiemate.views.IDetailedView



class DetailActivity : BaseActivity(), IDetailedView {
    private lateinit var storagePresenter: FbStorageWrite
    private lateinit var eggs: Eggs
    private lateinit var eggDeterminator: EggDeterminator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        eggs = intent.extras.get("Egg") as Eggs
        ResViewInit(eggs)

        // toolbar.setTitle(eggs.eggtag.toString())
        // toolbar.setSubtitle(eggs.displayTime())

        toast(eggs.isLegacyEgg().toString())
        eggDeterminator = EggDeterminator(this, eggs)
        if (eggs.isnewEgg){
            showProgress(true)
            eggs.remoteImgURL = eggs.localImgUri.lastPathSegment.toString()
            my_recycler_view.adapter.notifyDataSetChanged()
            eggDeterminator.upload()
        }
    }

    private fun ResViewInit(coolDataHere: Eggs){
        val viewManager = LinearLayoutManager(this)
        val viewAdapter = EggsDetailAdapter(coolDataHere, this)

        my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
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
        my_recycler_view.adapter.notifyDataSetChanged()
    }

    override fun toast(st: String) {
        println("toast"+Thread.currentThread().name)
        super.toast(st)
    }

    override fun showProgress(show: Boolean) {
        println("showProgress"+Thread.currentThread().name)
        when(show){
            //true-> pb1.visibility = View.VISIBLE
            //false -> pb1.visibility = View.GONE
        }
    }

}

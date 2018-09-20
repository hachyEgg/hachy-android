package ms.imagine.foodiemate.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import ms.imagine.foodiemate.presenters.*;
import ms.imagine.foodiemate.R;
import ms.imagine.foodiemate.adapter.EggsDetailAdapter;
import ms.imagine.foodiemate.data.Eggs;
import ms.imagine.foodiemate.views.IDetailedView;


public class DetailActivity extends BaseActivity implements IDetailedView {
    private static final String TAG = "DetailActivity";
    private FbStorageWrite storagePresenter;
    private Eggs eggs;
    private EggDeterminator eggDeterminator;
    RecyclerView my_recycler_view;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eggs = (Eggs) getIntent().getExtras().get("Egg");
        ResViewInit(eggs);

        // toolbar.setTitle(eggs.eggtag.toString())
        // toolbar.setSubtitle(eggs.displayTime())

        toast(""+eggs.isLegacyEgg());
        eggDeterminator = new EggDeterminator(this, eggs);

        if (eggs.isIsnewEgg()){
            showProgress(true);
            eggs.setRemoteImgURL(eggs.getLocalImgUri().getLastPathSegment());
            my_recycler_view.getAdapter().notifyDataSetChanged();
            eggDeterminator.upload();
        }
    }

    private void  ResViewInit(Eggs coolDataHere){
        LinearLayoutManager viewManager = new LinearLayoutManager(this);
        EggsDetailAdapter viewAdapter = new EggsDetailAdapter(coolDataHere, this);

        my_recycler_view = findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager ( viewManager );
        my_recycler_view.setAdapter(viewAdapter );
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override public void updateStatus(String state){
        Log.d(TAG, "updateStatus"+Thread.currentThread().getName());
        my_recycler_view.getAdapter().notifyDataSetChanged();
    }

    @Override public void toast(String st) {
        Log.d(TAG, "toast"+Thread.currentThread().getName());
        super.toast(st);
    }

    @Override
    public void showProgress(boolean show) {
        Log.d(TAG, "showProgress"+Thread.currentThread().getName());
    }

}


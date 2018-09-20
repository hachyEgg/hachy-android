package ms.imagine.foodiemate.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import ms.imagine.foodiemate.R;
import ms.imagine.foodiemate.adapter.ResViewAdapter;
import ms.imagine.foodiemate.callbacks.DbReadCallBacks;
import ms.imagine.foodiemate.data.Eggs;
import ms.imagine.foodiemate.presenters.FbDatabasePresenter;
import ms.imagine.foodiemate.presenters.FbAuthStatePresenter;
import ms.imagine.foodiemate.presenters.FbDatabaseRead;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;



class MainActivity extends BaseActivity implements DbReadCallBacks, ResViewAdapter.OnItemClicked {
    private RecyclerView my_recycler_view;
    private ProgressBar pb1;
    private FbDatabasePresenter fbdatabase;
    private FbAuthStatePresenter fbAuthStatePresenter;
    private ResViewAdapter viewAdapter;
    private HashSet<String> eggIndex;
    private ArrayList<Eggs> list;

    public static final String TAG = "MainActivity";
    public static final String TO_SIGN_OUT = "sign_out";
    public static final int TAKE_PIC_CAMERA = 0x9;
    public static final int SELECT_PIC_LOCAL = 0x7;
    public static final String TAKE_PIC_FINISHED = "picTaken";
    public static final String NULL = "null_Found";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 0x667;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 0x548;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        my_recycler_view = findViewById(R.id.my_recycler_view);
        pb1 = this.findViewById(R.id.pb1);

        //fab icon
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener ((on)->checkPermissionToProceed());

        // FB connection Presenters:
        eggIndex = new HashSet<>();
        fbAuthStatePresenter = new FbAuthStatePresenter();
        fbdatabase = new FbDatabaseRead(fbAuthStatePresenter.userState().getUid(), this);

        //Logic
        if (fbAuthStatePresenter.userState() == null) finish();
        list = new ArrayList();
        ResViewInit(list);
    }

    private void openCamera(){
        Intent i = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(i);
    }

    private void checkPermissionToProceed() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_STORAGE);
        } else {
            openCamera();
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode,
                                                     @NonNull String[]permissions, @NotNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkPermissionToProceed();
                } else {
                    toast(getString(R.string.plead_permission_camera));
                }
            }
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkPermissionToProceed();
                } else {
                    toast(getString(R.string.plead_permission_storage));
                }

            }
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "OnREsult");     // we should probably no do this
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == TAKE_PIC_CAMERA){
                String s = (String) data.getExtras().get(TAKE_PIC_FINISHED);
                Log.w(TAG, s);
            } else if (requestCode == SELECT_PIC_LOCAL) {
                Log.d(TAG, "imgSelect_OK");
                Uri uri = data.getData();
                if (uri!=null ) {
                    Intent i = new Intent(MainActivity.this, DetailActivity.class);
                    i.putExtra("Egg", new Eggs("coo", System.currentTimeMillis(), 0,uri));
                    Log.d(TAG, "start");
                    startActivity(i);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ResViewInit(ArrayList<Eggs>coolDataHere){
        LinearLayoutManager viewManager = new LinearLayoutManager(this);
        viewAdapter = new ResViewAdapter(coolDataHere, this);
        viewAdapter.setOnClick(this);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(viewManager);
        my_recycler_view.setAdapter(viewAdapter);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signout: {
                signOut();
                return true;
            }
            case R.id.action_imageselect: {
                startImagePicActivity();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        fbAuthStatePresenter.signOut();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.putExtra(TO_SIGN_OUT, true);
        finish();
        startActivity(i);
    }

    @Override public void retrieveEgg(String key, Eggs eggs){
        pb1.setVisibility(View.GONE);
        if(eggIndex.add(key)){
            list.add(0,eggs);
            viewAdapter.notifyDataSetChanged();
        }
    }

    @Override public void retrieveEggError() {
        toast(getString(R.string.failed_retrieve_eggs));
    }

    private void showEggDetail(Eggs eggs) {
        Intent i = new Intent(MainActivity.this, DetailActivity.class);
        i.putExtra("Egg", eggs);
        startActivity(i);
    }

    @Override public void onItemClick(int position) {
        showEggDetail(list.get(position));
    }
}


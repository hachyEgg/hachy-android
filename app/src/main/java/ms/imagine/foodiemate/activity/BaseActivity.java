package ms.imagine.foodiemate.activity;

/**
 * Created by eugen on 3/26/2018.
 */

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;
import ms.imagine.foodiemate.R;

import android.view.MenuInflater;


public class BaseActivity extends AppCompatActivity {
    void toast(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    void startImagePicActivity(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType ("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType ("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});


        startActivityForResult(chooserIntent, MainActivity.SELECT_PIC_LOCAL);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
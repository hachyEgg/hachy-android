package ms.imagine.foodiemate.presenters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import java.io.InputStream;
import java.net.URL;


public class FbStorageRead extends FbStoragePresenter {

    public void downloadImage(Context context, String imgURL, ImageView imgView) {
        Glide.with(context /* context */)
                .using(new FirebaseImageLoader())
                .load(imagesRef.child(imgURL))
                .into(imgView);
    }

    public Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream ois = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(ois, "src name");
        } catch (Exception e) {
            return null;
        }
    }

}
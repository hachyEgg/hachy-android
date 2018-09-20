package ms.imagine.foodiemate.presenters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import ms.imagine.foodiemate.api.Prediction;
import ms.imagine.foodiemate.callbacks.AzureCallBacks;
import ms.imagine.foodiemate.callbacks.DbWriteCallBacks;
import ms.imagine.foodiemate.callbacks.StWriteCallBacks;
import ms.imagine.foodiemate.data.Eggs;
import ms.imagine.foodiemate.data.EggStages;
import ms.imagine.foodiemate.views.IDetailedView;

import java.util.List;

public class EggDeterminator implements StWriteCallBacks, AzureCallBacks, DbWriteCallBacks {
    private FbDatabaseWrite databaseWrite;
    private AzurePresenter azure;
    private FbStorageWrite storagePresenter;
    private IDetailedView view;
    private Eggs eggs;
    private static final String TAG = "EggDeterminator";

    public EggDeterminator  (IDetailedView view, Eggs eggs){
        databaseWrite = new FbDatabaseWrite(new FbAuthStatePresenter().userState().getUid(),this);
        azure = new AzurePresenter(this);
        storagePresenter = new FbStorageWrite(this);
        this.view = view;
        this.eggs = eggs;
    }

    public void upload(){
        storagePresenter.uploadImage(eggs.getLocalImgUri());
    }


    public void download(Context context, ImageView image){
        storagePresenter.downloadImage(context, eggs.getRemoteImgURL(), image);
    }

    @Override public void uploadSuccess(Uri uri) {
        Log.w("EUGWARN_CAM", uri.toString());
        String remote_url = uri.toString();
        Log.d("remoteUrl" , uri.toString());

        try{
            view.toast("Image Successfully uploaded");
        } catch (Exception e){
            Log.e("error", e.toString(), e);
        }

        Log.d(TAG, "toUploadonAzure");

        Log.d(TAG, Thread.currentThread().getName());
        azure.postImageFromUrl(remote_url);
    }

    @Override public void uploadFailed() {
        view.showProgress(false);
        view.toast("UploadFailed");
    }

    @Override public void onAzureSuccess(List<Prediction> prediction) {
        Log.d(TAG, "AzureSuccess");
        EggStages egS = new EggStages(prediction);
        eggs.setStatus(egS.waEgg());
        String state = eggs.displayStatus();
        view.showProgress(false);
        view.updateStatus(state);

        databaseWrite.writeEgg(eggs);
    }

    @Override public void onAzureFailure(String str) {
        view.showProgress(false);
        view.toast("egg status determination failed");
    }

    @Override public void onDbWriteSuccess() {

    }

    @Override public void onDbWriteFailure() {

    }
}
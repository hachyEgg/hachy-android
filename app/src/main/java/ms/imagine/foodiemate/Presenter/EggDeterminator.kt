package ms.imagine.foodiemate.Presenter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import ms.imagine.foodiemate.api.Prediction
import ms.imagine.foodiemate.callbacks.AzureCallBacks
import ms.imagine.foodiemate.callbacks.DbWriteCallBacks
import ms.imagine.foodiemate.callbacks.StWriteCallBacks
import ms.imagine.foodiemate.data.Eggs
import ms.imagine.foodiemate.data.EggStages
import ms.imagine.foodiemate.views.IDetailedView

class EggDeterminator(val view: IDetailedView, val eggs: Eggs) : StWriteCallBacks, AzureCallBacks, DbWriteCallBacks {
    private lateinit var databaseWrite: FbDatabaseWrite
    private lateinit var  azure: AzurePresenter
    private lateinit var  storagePresenter: FbStorageWrite

    init{
        databaseWrite = FbDatabaseWrite(FbAuthStatePresenter().userState()!!.uid,this)
        azure = AzurePresenter(this)
        storagePresenter = FbStorageWrite(this)
    }

    fun upload(){
        storagePresenter.uploadImage(eggs.localImgUri)
    }


    fun download(context: Context, image: ImageView){
        storagePresenter.downloadImage(context, eggs.remoteImgURL, image)
    }

    override fun uploadSuccess(uri: Uri?) {
        Log.w("EUGWARN_CAM", uri.toString())
        val remote_url = uri.toString()
        println("remoteUrl" + uri.toString())

        try{
            view.toast("Image Successfully uploaded")
        } catch (e:Exception){println(e)}

        println("toUploadonAzure")

        println(Thread.currentThread().name)
        azure.postImageFromUrl(remote_url)
    }

    override fun uploadFailed() {
        view.showProgress(false)
        view.toast("UploadFailed")
    }

    override fun onAzureSuccess(prediction: List<Prediction>) {
        println("AzureSuccess")
        val egS = EggStages(prediction)
        eggs.status = egS.waEgg()
        val state = eggs.displayStatus()
        view.showProgress(false)
        view.updateStatus(state)

        databaseWrite.writeEgg(eggs)
    }

    override fun onAzureFailure(str: String) {
        view.showProgress(false)
        view.toast("egg status determination failed")
    }

    override fun onDbWriteSuccess() {

    }

    override fun onDbWriteFailure() {

    }
}
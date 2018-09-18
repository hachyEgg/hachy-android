package ms.imagine.foodiemate.data

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import ms.imagine.foodiemate.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by eugen on 3/30/2018.
 */


// What is left to do:
// Create a firebase API that lets streaming data to Azure ImageRecognition
// Train it
// Create 3 stages of the eggs
data class Eggs(var eggtag: String, var timestamp: Long, var status : Int): Parcelable {
    var remoteImgURL: String = "noImg"
    var localImgUri: Uri = Uri.parse(remoteImgURL)
    var isnewEgg = true
    var egg = ArrayList<Egg>()


    //no data for testing purpose
    override fun toString(): String {
        return "EGG: \n\teggtag: "+eggtag +"\n\ttimestamp: "+ timestamp + "\n\tstatus: "+ status
    }

    fun displayTime(): String{
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val formattedDate = df.format(timestamp)
        return formattedDate
    }

    fun isLegacyEgg(): Boolean{
        return (egg.isEmpty() || egg.size == 0)
    }

    fun displayStatus(): String{
        return when (status){
            0->"No Egg detected"
            1->"Egg discovered but with no VISIBLE DEVELOPMENT"
            2->"Egg has just initiated development"
            3->"Egg has matured Development"
            4->"Egg has quit"
            else->"No Egg detected"
        }
    }
    fun insertSnap( eggSnapshot: Egg){
        egg.add(eggSnapshot)
    }

    fun displayStatusThumbnail(context: Context): Drawable?{
        when (status){
            0->return ContextCompat.getDrawable(context, R.drawable.stg0_t)
            1->return ContextCompat.getDrawable(context, R.drawable.stg1_t)
            2->return ContextCompat.getDrawable(context, R.drawable.stg2_t)
            3->return ContextCompat.getDrawable(context, R.drawable.stg3_t)
            4->return ContextCompat.getDrawable(context, R.drawable.stg4_t)
        }
        return null
    }


    constructor(eggtag: String, timestamp: Long, status: Int, url: String) :
            this(eggtag, timestamp, status) {
        this.remoteImgURL = url
    }

    constructor(eggtag: String, timestamp: Long, status: Int, uri: Uri) :
            this(eggtag, timestamp, status) {
        this.localImgUri = uri
    }

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readLong(),parcel.readInt()) {
        remoteImgURL = (parcel.readString())
        localImgUri = Uri.parse(parcel.readString())
        isnewEgg = (parcel.readInt() == 1)
        egg = arrayListOf<Egg>().apply {
            parcel.readList(this, Egg::class.java.classLoader)
        }
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(eggtag)
        p0?.writeLong(timestamp)
        p0?.writeInt(status)
        p0?.writeString(remoteImgURL)
        p0?.writeString(localImgUri.toString())
        p0?.writeInt((if(isnewEgg) 1 else 0))
        p0?.writeList(egg)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Eggs> {
        override fun createFromParcel(parcel: Parcel): Eggs {
            return Eggs(parcel)
        }

        override fun newArray(size: Int): Array<Eggs?> {
            return arrayOfNulls(size)
        }
    }
}

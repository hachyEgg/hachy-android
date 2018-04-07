package ms.imagine.foodiemate.data

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import ms.imagine.foodiemate.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by eugen on 3/30/2018.
 */


// What is left to do:
// Create a firebase API that lets streaming data to Azure ImageRecognition
// Train it
// Create 3 stages of the eggs
class Egg(): Parcelable {
    var eggtag = "Healthy Egg"
    var timestamp: Long = 100
    var status = 0
    var imgURL: String = "noImg"


    //no data for testing purpose
    override fun toString(): String {
        return "EGG: \n\teggtag: "+eggtag +"\n\ttimestamp: "+ timestamp + "\n\tstatus: "+ status;
    }

    fun displayTime(): String{
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val formattedDate = df.format(timestamp)
        return formattedDate
    }

    fun displayStatus(): String{
        when (status){
            0->return "No Egg detected"
            1->return "Egg discovered but with no VISIBLE DEVELOPMENT"
            2->return "Egg has just initiated development"
            3->return "Egg has matured Development"
            4->return "Egg has quit"
        }
        return "No Egg detected"
    }

    fun displayStatusThumbnail(context: Context): Drawable?{
        when (status){
            0->return ContextCompat.getDrawable(context, R.drawable.stg0);
            1->return ContextCompat.getDrawable(context, R.drawable.stg1);
            2->return ContextCompat.getDrawable(context, R.drawable.stg2);
            3->return ContextCompat.getDrawable(context, R.drawable.stg3);
            4->return ContextCompat.getDrawable(context, R.drawable.stg4);
        }
        return null
    }


    constructor(eggtag: String, timestamp: Long, status: Int) : this() {
        this.eggtag = eggtag
        this.timestamp = timestamp
        this.status = status
    }

    constructor(eggtag: String, timestamp: Long, status: Int, url: String) : this() {
        this.eggtag = eggtag
        this.timestamp = timestamp
        this.status = status
        this.imgURL = url
    }

    fun zip(): String{
        return eggtag + sep + timestamp + sep + status + sep + imgURL;
    }

    constructor(compactString: String) : this() {
        var arr = compactString.split(sep)
        eggtag = arr[0]
        timestamp = arr[1].toLong()
        status = arr[2].toInt()
        imgURL = (arr[3])
    }

    constructor(parcel: Parcel) : this() {
        eggtag = parcel.readString()
        timestamp = parcel.readLong()
        status = parcel.readInt()
        imgURL = (parcel.readString())
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(eggtag)
        p0?.writeLong(timestamp)
        p0?.writeInt(status)
        p0?.writeString(imgURL)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Egg> {
        override fun createFromParcel(parcel: Parcel): Egg {
            return Egg(parcel)
        }

        override fun newArray(size: Int): Array<Egg?> {
            return arrayOfNulls(size)
        }
        const val sep = "lolxd";

    }
}

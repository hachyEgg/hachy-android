package ms.imagine.foodiemate.data

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.IgnoreExtraProperties

import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by eugen on 3/30/2018.
 */

class Egg(): Parcelable {
    var eggtag = "Healthy Egg"
    var timestamp: Long = 100
    var status = "This egg has no embryo development"
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


    constructor(eggtag: String, timestamp: Long, status: String) : this() {
        this.eggtag = eggtag
        this.timestamp = timestamp
        this.status = status
    }

    constructor(eggtag: String, timestamp: Long, status: String, url: String) : this() {
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
        status = arr[2]
        imgURL = (arr[3])
    }

    constructor(parcel: Parcel) : this() {
        eggtag = parcel.readString()
        timestamp = parcel.readLong()
        status = parcel.readString()
        imgURL = (parcel.readString())
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(eggtag)
        p0?.writeLong(timestamp)
        p0?.writeString(status)
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

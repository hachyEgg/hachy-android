package ms.imagine.foodiemate.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import ms.imagine.foodiemate.R;
import java.text.SimpleDateFormat;
import java.util.*;
import kotlin.collections.ArrayList


/**
 * Created by eugen on 3/30/2018.
 */


// What is left to do:
// Create a firebase API that lets streaming data to Azure ImageRecognition
// Train it
// Create 3 stages of the eggs
public class Eggs implements Parcelable {
    String remoteImgURL = "noImg";
    Uri localImgUri;
    boolean isnewEgg = true;
    private ArrayList<Egg> egg;

    String eggtag;
    long timestamp;
    int status;


    Eggs(String eggtag, long timestamp, int status){
        this.eggtag = eggtag;
        this.timestamp = timestamp;
        this.status = status;

        Uri.parse(remoteImgURL);
        egg = new ArrayList<>();
    }

    //no data for testing purpose
    @Override public String toString() {
        return "EGG: \n\teggtag: "+eggtag +"\n\ttimestamp: "+ timestamp + "\n\tstatus: "+ status;
    }

    String displayTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String formattedDate = df.format(timestamp);
        return formattedDate;
    }

    public boolean isLegacyEgg() {
        return (egg.isEmpty() || egg.size == 0)
    }

    public String displayStatus(){
         switch (status){
             case 0: return "No Egg detected" ;
             case 1: return "Egg discovered but with no VISIBLE DEVELOPMENT";
             case 2: return "Egg has just initiated development";
             case 3: return "Egg has matured Development";
             case 4: return "Egg has quit";
            default: return "No Egg detected";
        }
    }
    public void insertSnap(Egg eggSnapshot){
        egg.add(eggSnapshot);
    }

    Drawable displayStatusThumbnail(Context context) {
        switch (status){
            case 0: return ContextCompat.getDrawable(context, R.drawable.stg0_t);
            case 1: return ContextCompat.getDrawable(context, R.drawable.stg1_t);
            case 2: return ContextCompat.getDrawable(context, R.drawable.stg2_t);
            case 3: return ContextCompat.getDrawable(context, R.drawable.stg3_t);
            case 4: return ContextCompat.getDrawable(context, R.drawable.stg4_t);
        }
        return null;
    }


    public Eggs(eggtag: String, timestamp: Long, status: Int, url: String) :
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

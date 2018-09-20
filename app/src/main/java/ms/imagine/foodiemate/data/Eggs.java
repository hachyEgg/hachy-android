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


/**
 * Created by eugen on 3/30/2018.
 */


// What is left to do:
// Create a firebase API that lets streaming data to Azure ImageRecognition
// Train it
// Create 3 stages of the eggs
public class Eggs implements Parcelable {
    private String remoteImgURL;
    private Uri localImgUri;
    private boolean isnewEgg = true;
    private ArrayList<Egg> egg;

    private String eggtag;
    private long timestamp;
    private int status;

    public String getRemoteImgURL() {
        return remoteImgURL;
    }

    public void setRemoteImgURL(String remoteImgURL) {
        this.remoteImgURL = remoteImgURL;
    }

    public Uri getLocalImgUri() {
        return localImgUri;
    }

    public void setLocalImgUri(Uri localImgUri) {
        this.localImgUri = localImgUri;
    }

    public boolean isIsnewEgg() {
        return isnewEgg;
    }

    public void setIsnewEgg(boolean isnewEgg) {
        this.isnewEgg = isnewEgg;
    }

    public ArrayList<Egg> getEgg() {
        return egg;
    }

    public void setEgg(ArrayList<Egg> egg) {
        this.egg = egg;
    }

    public String getEggtag() {
        return eggtag;
    }

    public void setEggtag(String eggtag) {
        this.eggtag = eggtag;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //no data for testing purpose
    @Override public String toString() {
        return "EGG: \n\teggtag: "+eggtag +"\n\ttimestamp: "+ timestamp + "\n\tstatus: "+ status;
    }

    public String displayTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String formattedDate = df.format(timestamp);
        return formattedDate;
    }

    public boolean isLegacyEgg() {
        return (egg.isEmpty());
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

    public Drawable displayStatusThumbnail(Context context) {
        switch (status){
            case 0: return ContextCompat.getDrawable(context, R.drawable.stg0_t);
            case 1: return ContextCompat.getDrawable(context, R.drawable.stg1_t);
            case 2: return ContextCompat.getDrawable(context, R.drawable.stg2_t);
            case 3: return ContextCompat.getDrawable(context, R.drawable.stg3_t);
            case 4: return ContextCompat.getDrawable(context, R.drawable.stg4_t);
        }
        return null;
    }

    public Eggs(String eggtag, long timestamp, int status){
        this(eggtag, timestamp, status, "");
    }


    public Eggs(String eggtag, long timestamp, int status, String remoteImgURL) {
        this(eggtag, timestamp, status, remoteImgURL, null, false, new ArrayList<Egg>());
    }

    public Eggs (String eggtag, long timestamp, int status, Uri uri) {
            this(eggtag, timestamp, status, null, uri, false, new ArrayList<Egg>());
    }



    public Eggs(String eggtag, long timestamp, int status, String remoteImgURL, Uri localUri, boolean isNewEgg, ArrayList<Egg> list_egg){
        this.eggtag = eggtag;
        this.timestamp = timestamp;
        this.status = status;
        this.remoteImgURL = remoteImgURL;
        this.localImgUri  = localUri;
        this.isnewEgg = isNewEgg;
        this.egg = list_egg;
    }

    public Eggs (Parcel parcel) {
        this(
                parcel.readString(),
                parcel.readLong(),
                parcel.readInt(),
                parcel.readString(),
                Uri.parse(parcel.readString()),
                parcel.readInt() == 1,
                parcel.readArrayList(Eggs.class.getClassLoader()));
    }


    @Override
    public void writeToParcel(Parcel p0, int p1) {
        p0.writeString(eggtag);
        p0.writeLong(timestamp);
        p0.writeInt(status);
        p0.writeString(remoteImgURL);
        p0.writeString(localImgUri.toString());
        p0.writeInt((isnewEgg? 1 :0));
        p0.writeList(egg);
    }

    @Override public int describeContents() { return 0; }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override public Eggs createFromParcel(Parcel parcel)  {
            return new Eggs(parcel);
        }

        @Override public Eggs[] newArray (int size){
            return new Eggs[size];
        }
    };

}

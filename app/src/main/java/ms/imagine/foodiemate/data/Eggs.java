package ms.imagine.foodiemate.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import ms.imagine.foodiemate.R;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@AllArgsConstructor
public class Eggs implements Parcelable {
    private static final String[] STATUS = {"No Egg detected", "Egg discovered but with no VISIBLE DEVELOPMENT",
            "Egg has just initiated development", "Egg has matured Development", "Egg has quit", "No Egg detected" };
    private static int DRAWABLE_THUMBNAIL[] = {R.drawable.stg0_t, R.drawable.stg1_t, R.drawable.stg2_t, R.drawable.stg3_t, R.drawable.stg4_t};

    // Attributes
    private String eggtag;
    private long timestamp;
    private int status;
    private String remoteImgURL;
    private Uri localImgUri;
    private boolean isnewEgg = true;
    private ArrayList<Egg> egg;

    public Eggs(){}

    public Eggs(String eggtag, long timestamp, int status){
        this(eggtag, timestamp, status, "", null);
    }


    public Eggs(String eggtag, long timestamp, int status, String remoteImgURL, Uri uri) {
        this(eggtag, timestamp, status, remoteImgURL, uri, false, new ArrayList<>());
    }

    public String displayTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.format(timestamp);
    }

    public boolean isLegacyEgg() {
        return (egg.isEmpty());
    }

    public String status(){
         return STATUS[status];
    }

    public void insertSnap(Egg eggSnapshot){
        egg.add(eggSnapshot);
    }

    public @DrawableRes int getThumbnail(){
        return DRAWABLE_THUMBNAIL[status];
    }

    // Parcelable boiler
    public Eggs (Parcel p) {
        this(p.readString(), p.readLong(),p.readInt(),p.readString(),Uri.parse(p.readString()),
                p.readInt() == 1, p.readArrayList(Eggs.class.getClassLoader()));
    }

    @Override public void writeToParcel(Parcel p0, int p1) {
        p0.writeString(eggtag);
        p0.writeLong(timestamp);
        p0.writeInt(status);
        p0.writeString(remoteImgURL);
        p0.writeString(String.valueOf(localImgUri));
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

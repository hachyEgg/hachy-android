package ms.imagine.foodiemate.data;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import lombok.Data;
import ms.imagine.foodiemate.R;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Created by eugen on 3/30/2018.
 */


// What is left to do:
// Create a firebase API that lets streaming data to Azure ImageRecognition
// Train it
// Create 3 stages of the eggs
@Data
public class Egg implements Parcelable {
    private String eggtag;
    private long timestamp;
    private int status;
    private String imgURL;

    private static final String sep = "lolxd";


    public String displayTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String formattedDate = df.format(timestamp);
        return formattedDate;
    }

    public String displayStatus() {
        switch (status) {
            case 0:
                return "No Egg detected";
            case 1:
                return "Egg discovered but with no VISIBLE DEVELOPMENT";
            case 2:
                return "Egg has just initiated development";
            case 3:
                return "Egg has matured Development";
            case 4:
                return "Egg has quit";
        }
        return "No Egg detected";
    }

    Drawable displayStatusThumbnail(Context context) {
        switch (status) {
            case 0:
                return ContextCompat.getDrawable(context, R.drawable.stg0);
            case 1:
                return ContextCompat.getDrawable(context, R.drawable.stg1);
            case 2:
                return ContextCompat.getDrawable(context, R.drawable.stg2);
            case 3:
                return ContextCompat.getDrawable(context, R.drawable.stg3);
            case 4:
                return ContextCompat.getDrawable(context, R.drawable.stg4);
        }
        return null;
    }


    String zip() {
        return eggtag + sep + timestamp + sep + status + sep + imgURL;
    }

    Egg(String compactString) {
        String[] arr = compactString.split(sep);
        eggtag = arr[0];
        timestamp = Long.parseLong(arr[1]);
        status = Integer.parseInt(arr[2]);
        imgURL = (arr[3]);
    }

    Egg(Parcel parcel) {
        eggtag = parcel.readString();
        timestamp = parcel.readLong();
        status = parcel.readInt();
        imgURL = (parcel.readString());
    }

    @Override
    public void writeToParcel(Parcel p0, int p1) {
        p0.writeString(eggtag);
        p0.writeLong(timestamp);
        p0.writeInt(status);
        p0.writeString(imgURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Egg> CREATOR
            = new Parcelable.Creator<Egg>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Egg createFromParcel(Parcel in) {
            return new Egg(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Egg[] newArray(int size) {
            return new Egg[size];
        }
    };


}



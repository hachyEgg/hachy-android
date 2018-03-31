package ms.imagine.foodiemate.data;

import android.util.Log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eugen on 3/30/2018.
 */

public class Egg {

    String eggName = "Healthy Egg";
    String timestamp = "2018-04-20@UTC19:01:22";
    String status = "This egg has no embryo development";

    //no data for testing purpose
    public Egg(){
        timeStampGenerator();
    }

    public String getTimeStamp() {
        return timestamp;
    }



    public String geteggName() {
        return eggName;
    }

    public String getStatus(){
        return status;
    }

    public void timeStampGenerator(){
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);
        timestamp = sdf.format(resultdate);
        Log.w("TIME", timestamp);
    }
}

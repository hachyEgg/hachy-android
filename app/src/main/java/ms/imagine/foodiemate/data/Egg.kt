package ms.imagine.foodiemate.data

import android.util.Log
import com.google.firebase.database.IgnoreExtraProperties

import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by eugen on 3/30/2018.
 */

class Egg {
    var eggtag = "Healthy Egg"
    var timestamp = "2018-04-20@UTC19:01:22"
    var status = "This egg has no embryo development"

    //no data for testing purpose


    override fun toString(): String {
        return "EGG: \n\teggtag: "+eggtag +"\n\ttimestamp: "+ timestamp + "\n\tstatus: "+ status;
    }

    constructor(isTest: Boolean){
        timeStampGenerator()
    }

    constructor(eggtag: String, timestamp: String, status: String) {
        this.eggtag = eggtag
        this.timestamp = timestamp
        this.status = status
    }


    fun timeStampGenerator() {
        val yourmilliseconds = System.currentTimeMillis()
        val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm")
        val resultdate = Date(yourmilliseconds)
        timestamp = sdf.format(resultdate)
        Log.w("TIME", timestamp)
    }
}

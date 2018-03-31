package ms.imagine.foodiemate.data

import android.util.Log

import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by eugen on 3/30/2018.
 */

class Egg {

    var mEggName = "Healthy Egg"
    var timeStamp = "2018-04-20@UTC19:01:22"
    var status = "This egg has no embryo development"

    //no data for testing purpose
    constructor() {
        timeStampGenerator()
    }

    constructor(eggName: String) {
        mEggName = eggName
    }


    fun geteggName(): String {
        return mEggName
    }

    fun timeStampGenerator() {
        val yourmilliseconds = System.currentTimeMillis()
        val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm")
        val resultdate = Date(yourmilliseconds)
        timeStamp = sdf.format(resultdate)
        Log.w("TIME", timeStamp)
    }
}

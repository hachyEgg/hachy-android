package ms.imagine.foodiemate.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

//Used for Retrieving background data

class BgData(val context: Context) {
    internal  var sp=PreferenceManager.getDefaultSharedPreferences(context)

    // get data but does not modify obtained data
    operator fun get(key: String, defualtValue: String): String {
        return sp.getString(key, defualtValue)
    }

    // get data and reset it to natural value
    fun retrieve(context: Context, key: String, naturalValue: String): String {
        val value = get(key, naturalValue)
        if (value != naturalValue) {
            sp.edit().putString(key, naturalValue).apply()
        }
        return value
    }

    // write given Value, and check if correctly Written
    fun write(key: String, toWriteValue: String): Boolean {
        sp.edit().putString(key, toWriteValue).apply()
        return sp.getString(key, "null") == toWriteValue
    }
}

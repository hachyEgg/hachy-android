package ms.imagine.foodiemate.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

//Used for Retrieving background data

object BgData {
    internal lateinit var sp: SharedPreferences



    private fun init(context: Context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context)
    }

    // get data but does not modify obtained data
    operator fun get(context: Context, key: String, defualtValue: String): String {
        init(context)
        return sp.getString(key, defualtValue)
    }

    // get data and reset it to natural value
    fun retrieve(context: Context, key: String, naturalValue: String): String {
        val value = get(context, key, naturalValue)
        if (value != naturalValue) {
            sp.edit().putString(key, naturalValue).apply()
        }
        return value
    }

    // write given Value, and check if correctly Written
    fun write(context: Context, key: String, toWriteValue: String): Boolean {
        init(context)
        sp.edit().putString(key, toWriteValue).apply()
        return sp.getString(key, "null") == toWriteValue
    }
}

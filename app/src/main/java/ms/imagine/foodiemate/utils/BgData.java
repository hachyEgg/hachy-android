package ms.imagine.foodiemate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//Used for Retrieving background data

public class BgData {
    static SharedPreferences sp;

    static void init(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // get data but does not modify obtained data
    public static String get (Context context, String key, String defualtValue){
        init(context);
        return sp.getString(key, defualtValue);
    }

    // get data and reset it to natural value
    public static String retrieve(Context context, String key, String naturalValue){
        String value = get(context, key, naturalValue);
        if (!value.equals(naturalValue)){
            sp.edit().putString(key, naturalValue).apply();
        }
        return value;
    }

    // write given Value, and check if correctly Written
    public static boolean write(Context context, String key, String toWriteValue){
        init(context);
        sp.edit().putString(key, toWriteValue).apply();
        return (sp.getString(key, "null").equals(toWriteValue));
    }
}

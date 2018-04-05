package ms.imagine.foodiemate.utils

import android.util.Log

object Eulog {
    private val WARN = "EUGWARN"

    fun w(s: String) {
        Log.w(WARN, s)
    }

    fun d(s: String) {
        Log.d(WARN, s)
    }

    fun v(s: String) {
        Log.v(WARN, s)
    }
}

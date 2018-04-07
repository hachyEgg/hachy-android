package ms.imagine.foodiemate.utils

import java.text.SimpleDateFormat
import java.util.*

public object Time {
    fun timehrs(): String {
        return SimpleDateFormat("HH:mm:ss:SSS").format(Date(System.currentTimeMillis()))
    }
}

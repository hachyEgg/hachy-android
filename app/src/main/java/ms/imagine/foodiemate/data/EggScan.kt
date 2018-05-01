package ms.imagine.foodiemate.data

import android.graphics.drawable.Drawable

class EggScan(var type:Int, var status: String, var ico: Drawable, var url: String) {


    companion object {
        val TYPE_HEAD = 1
        val TYPE_BODY = 0
    }

}
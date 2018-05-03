package ms.imagine.foodiemate.data

import android.net.Uri

class Egg {
    var timestamp: Long = 100
    var status = 0
    var remoteImgURL: String = "noImg"

    constructor(toString: String, toInt: Int, toLong: Long){
        remoteImgURL = toString
        status = toInt
        timestamp = toLong
    }


}
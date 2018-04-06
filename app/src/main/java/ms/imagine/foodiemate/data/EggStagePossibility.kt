package ms.imagine.foodiemate.data

import org.json.JSONObject
import kotlin.math.max

class EggStagePossibility {
    var stage:DoubleArray = doubleArrayOf(.0,.1,.0,.0);

    //Fun JSON translator Needed Here:


    fun isEgg():Boolean = (stage[0]>IS_EGG || stage[1]>IS_EGG || stage[2]>IS_EGG || stage[3]>IS_EGG);
    fun waEgg():Int = stage.indices.maxBy { stage[it] } ?: -1
    companion object {
        const val IS_EGG = 0.11;
    }
}
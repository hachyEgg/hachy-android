package ms.imagine.foodiemate.data

import org.json.JSONObject

class EggStages(json: String) {
    val jsonParent: JSONObject

    var stage:DoubleArray = doubleArrayOf(.0,.1,.0,0.1)

    init{
        jsonParent = JSONObject(json)
        attempt_toConvert()
    }

    fun attempt_toConvert(){
        val ja = jsonParent.getJSONArray("Predictions")
        println(ja)
        for (i in 0..3){
            val paramTag = ja?.getJSONObject(i)?.getString("Tag")
            val paramPredicability = ja?.getJSONObject(i)?.getDouble("Probability")
            println(paramTag + ":" + paramPredicability)

            if (paramPredicability != null){
                if (paramTag.equals("egg") ) {
                    stage[0] = paramPredicability
                } else if (paramTag.equals("egg_mid") ) {
                    stage[2] = paramPredicability
                } else if (paramTag.equals("egg_early")){
                    stage[1] = paramPredicability
                } else if (paramTag.equals("egg_late")){
                    stage[3] = paramPredicability
                }
            }
        }
    }

    fun isEgg():Boolean = (stage[0]>0.8)
    fun waEgg():Int {
        var i = 0
        var tempStore = 0.0
        for ((index, value) in stage.withIndex()){
            if(index !=0 && value > tempStore) {
                tempStore = value
                i = index
            }
        }
        return i
    }
    companion object {
        const val IS_EGG = 0.11
    }
}
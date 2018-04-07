package ms.imagine.foodiemate.data

import org.json.JSONObject

class EggStages(json: String) {
    val jsonParent: JSONObject

    var stage:DoubleArray = doubleArrayOf(.0,.1,.0);

    init{
        jsonParent = JSONObject(json)
        attempt_toConvert()
    }

    fun attempt_toConvert(){
        val ja = jsonParent.getJSONArray("Predictions")
        for (i in 0..1){
            val paramTag = ja?.getJSONObject(i)?.getString("Tag")
            val paramPredicability = ja?.getJSONObject(i)?.getDouble("Probability")
            println(paramTag + ":" + paramPredicability)

            if (paramPredicability != null){
                if (paramTag.equals("egg_mid") ) {
                    stage[1] = paramPredicability
                } else if (paramTag.equals("egg_early")){
                    stage[0] = paramPredicability
                } else if (paramTag.equals("egg_late")){
                    stage[2] = paramPredicability
                }
            }
        }
    }

    fun isEgg():Boolean = (stage[0]>IS_EGG || stage[1]>IS_EGG || stage[2]>IS_EGG);
    fun waEgg():Int = stage.indices.maxBy { stage[it] } ?: -1
    companion object {
        const val IS_EGG = 0.11;
    }
}
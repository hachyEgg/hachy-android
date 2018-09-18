package ms.imagine.foodiemate.data

class EggStages(val prediction: List<Prediction>) {
    var stage:DoubleArray = doubleArrayOf(.0,.1,.0,0.1)

    init{
        attempt_toConvert()
    }

    fun attempt_toConvert(){
        for (i in prediction){
            val paramTag = i.tag
            val paramPredicability = i.probability
            if (paramTag == null || paramPredicability == null){
                continue
            } else {
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
        println("" + stage.get(0)+"," + stage.get(1) +","+ stage.get(2)+"," + stage.get(3))
    }

    fun isEgg():Boolean = (stage[0]>IS_EGG)
    fun waEgg():Int {
        var i = 0
        var tempStore = 0.0
        if (!isEgg() ){return 0}
        for ((index, value) in stage.withIndex()){
            if(index !=0 && value > tempStore) {
                tempStore = value
                i = index
            }
        }
        return i
    }
    companion object {
        const val IS_EGG = 0.80
    }
}
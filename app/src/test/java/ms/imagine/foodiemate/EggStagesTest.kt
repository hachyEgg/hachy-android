package ms.imagine.foodiemate

import ms.imagine.foodiemate.data.EggStages
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * EggStagePossibility test
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class EggStagesTest {
    //val eggS: EggStages
    val sampleReturnJson = "{\n" +
            "\t\"Id\":\"7cf85d90-e279-47aa-bace-733db544ae61\",\n" +
            "\t\"Project\":\"6ed4e03b-5c8d-4cc1-9cc0-ef527e4f625f\",\n" +
            "\t\"Iteration\":\"31f38b48-43e6-43ff-a3c3-4638be6d0f52\",\n" +
            "\t\"Created\":\"2018-04-06T21:31:43.3766604Z\",\n" +
            "\t\"Predictions\":[{\n" +
            "\t\t\"TagId\":\"ebd33ac5-914c-47ac-857f-1c536c41e68c\",\n" +
            "\t\t\"Tag\":\"egg_mid\",\"Probability\":0.03388239\n" +
            "\t},{\n" +
            "\t\t\"TagId\":\"e0441825-09ee-4cbd-833e-6bf6bd45e610\",\n" +
            "\t\t\"Tag\":\"egg_early\",\"Probability\":0.00167871953\n" +
            "\t}]\n" +
            "}"

    init {
        //eggS = EggStages(sampleReturnJson)
    }


    @Test
    fun eggIndex_IsNotNull(){
        //val obj:JSONObject = eggS.jsonParent
        //val str = obj.toString()
        //println(str)
        //assertNotNull(eggS)
    }


    @Throws(Exception::class)
    fun maxEggIndex_IsCorrect() {
        //assertEquals(1, eggS.waEgg())
    }

    @Test
    fun getRghtEggprediction(){
        //assertFalse(eggS.isEgg())
    }
}
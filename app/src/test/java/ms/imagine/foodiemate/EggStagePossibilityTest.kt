package ms.imagine.foodiemate

import ms.imagine.foodiemate.data.EggStagePossibility
import org.junit.Test

import org.junit.Assert.*

/**
 * EggStagePossibility test
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class EggStagePossibilityTest {
    @Test
    @Throws(Exception::class)
    fun maxEggIndex_IsCorrect(){
        var cool  = EggStagePossibility()
        assertEquals(1, cool.waEgg());
    }

    @Test
    @Throws(Exception::class)
    fun getRghtEggprediction(){
        var cool  = EggStagePossibility()
        assertFalse(cool.isEgg())
    }

    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }
}
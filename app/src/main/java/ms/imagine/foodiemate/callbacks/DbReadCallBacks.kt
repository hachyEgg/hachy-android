package ms.imagine.foodiemate.callbacks

import ms.imagine.foodiemate.data.Eggs

interface  DbReadCallBacks {
    fun retrieveEgg(key: String, eggs: Eggs)
    fun retrieveEggError()
}
package ms.imagine.foodiemate.callbacks

import ms.imagine.foodiemate.data.Egg

interface  DbReadCallBacks {
    fun retrieveEgg(key: String, egg: Egg)
    fun retrieveEggError()
}
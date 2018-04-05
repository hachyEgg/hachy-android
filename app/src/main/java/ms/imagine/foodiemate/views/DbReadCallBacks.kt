package ms.imagine.foodiemate.views

import com.google.firebase.database.DatabaseException
import ms.imagine.foodiemate.data.Egg

interface  DbReadCallBacks {
    fun retrieveEgg(key: String, egg: Egg)
    fun retrieveEggError(e: DatabaseException)
}
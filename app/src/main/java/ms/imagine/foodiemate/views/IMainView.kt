package ms.imagine.foodiemate.views

import com.google.firebase.database.DatabaseException
import ms.imagine.foodiemate.data.Egg

interface IMainView {
    fun signOut()
    fun retrieveEgg(key: String, egg: Egg)
    fun retrieveEggError(e: DatabaseException)
    fun showEggDetail(egg: Egg)
}
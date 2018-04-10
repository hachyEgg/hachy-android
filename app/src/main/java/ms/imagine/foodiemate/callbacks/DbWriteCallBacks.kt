package ms.imagine.foodiemate.callbacks

import ms.imagine.foodiemate.data.Egg

interface  DbWriteCallBacks {
    fun onDbWriteSuccess()
    fun onDbWriteFailure()
}
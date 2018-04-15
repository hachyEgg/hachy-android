package ms.imagine.foodiemate.callbacks

import ms.imagine.foodiemate.api.Prediction

interface AzureCallBacks{
    fun onAzureSuccess(prediction: List<Prediction>)
    fun onAzureFailure(str: String)
}
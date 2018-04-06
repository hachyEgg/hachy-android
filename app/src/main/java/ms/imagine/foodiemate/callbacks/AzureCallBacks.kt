package ms.imagine.foodiemate.callbacks

import ms.imagine.foodiemate.data.EggStagePossibility

interface AzureCallBacks{
    fun onAzureSuccess(eggStagePossibility: EggStagePossibility);
    fun onAzureFailure();
}
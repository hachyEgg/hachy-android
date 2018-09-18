package ms.imagine.foodiemate.api

import ms.imagine.foodiemate.data.AzurePrediction
import retrofit2.Call
import retrofit2.http.*


interface AzureClient{
    companion object {
        val baseURL = "https://southcentralus.api.cognitive.microsoft.com/" +
                "customvision/v1.1/Prediction/bf3f0d19-cb5b-4dee-95c7-23b530221fa4/"
    }

    @Headers(
            "Prediction-Key: 4e26554b300f47478e5c880d2a6492d7",
            "Content-Type: application/json"
    )

    @POST("url?iterationId=b1ac0250-d769-4594-9322-0788aee2dda1")
    fun postImageFromUrl(@Body url: HashMap<String, String>): Call<AzurePrediction>

    @POST("url?iterationId=b1ac0250-d769-4594-9322-0788aee2dda1")
    fun postImageFromFile(@Body url: HashMap<String, String>): Call<AzurePrediction>

}
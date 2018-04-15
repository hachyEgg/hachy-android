package ms.imagine.foodiemate.api

import retrofit2.Call
import retrofit2.http.*


interface AzureClient{

    @Headers(
            "Prediction-Key: 4e26554b300f47478e5c880d2a6492d7",
            "Content-Type: application/json"
    )

    @POST("url?iterationId=b1ac0250-d769-4594-9322-0788aee2dda1")
    fun postImageFromUrl(@Body url: HashMap<String, String>): Call<AzurePrediction>
}
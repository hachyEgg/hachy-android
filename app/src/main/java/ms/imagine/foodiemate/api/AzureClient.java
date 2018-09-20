package ms.imagine.foodiemate.api;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;


public interface AzureClient{

    String baseURL = "https://southcentralus.api.cognitive.microsoft.com/" +
                "customvision/v1.1/Prediction/bf3f0d19-cb5b-4dee-95c7-23b530221fa4/";


    @Headers({
            "Prediction-Key: 4e26554b300f47478e5c880d2a6492d7",
            "Content-Type: application/json"}
    )

    @POST("url?iterationId=b1ac0250-d769-4594-9322-0788aee2dda1")
    Call<AzurePrediction> postImageFromUrl(@Body HashMap<String, String> url);

    @POST("url?iterationId=b1ac0250-d769-4594-9322-0788aee2dda1")
    Call<AzurePrediction> postImageFromFile(@Body HashMap<String, String> url);

}
package ms.imagine.foodiemate.presenters;


import android.app.Notification;
import android.util.Log;
import ms.imagine.foodiemate.api.AzureClient;
import ms.imagine.foodiemate.api.AzurePrediction;
import ms.imagine.foodiemate.api.Prediction;
import ms.imagine.foodiemate.callbacks.AzureCallBacks;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.List;

public class AzurePresenter implements retrofit2.Callback<AzurePrediction> {
    private AzureClient azureClient;
    private AzureCallBacks callBacks; 

    AzurePresenter(AzureCallBacks callBacks)  {
        this.callBacks = callBacks;
         Retrofit.Builder builder = new Retrofit.Builder()
                 .baseUrl(AzureClient.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        azureClient = retrofit.create(AzureClient.class);
    }
    
    public void postImageFromUrl(String remote_url) {
        azureClient.postImageFromUrl(buildUrl(remote_url)).enqueue(this);
    }

    private HashMap<String, String> buildUrl(String remote_url) {
        HashMap<String, String> map = new HashMap<>();
        map.put("url", remote_url);
        return map;
    }

    @Override public void onFailure( retrofit2.Call<AzurePrediction> call, Throwable t) {
        callBacks.onAzureFailure("Cannot Call");
    }

    @Override public void onResponse(retrofit2.Call<AzurePrediction> call, retrofit2.Response<AzurePrediction> response) {
        AzurePrediction azurePrediction = response.body();
        Log.d("PDC" , response.toString() + azurePrediction);
        if (azurePrediction == null || azurePrediction.predictions == null){
            callBacks.onAzureFailure(response.toString());
        } else {
            List predictions = azurePrediction.predictions;
            callBacks.onAzureSuccess(predictions);
        }
    }
}
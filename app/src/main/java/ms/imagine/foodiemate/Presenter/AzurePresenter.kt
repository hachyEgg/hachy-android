package ms.imagine.foodiemate.Presenter


import ms.imagine.foodiemate.callbacks.AzureCallBacks
import ms.imagine.foodiemate.utils.Eulog
import okhttp3.*
import java.io.IOException

class AzurePresenter(val callBacks: AzureCallBacks) {

    companion object {
        const val AZURE_VIEWREC_URL = "https://southcentralus.api.cognitive.microsoft.com/customvision/v1.1/Prediction/6ed4e03b-5c8d-4cc1-9cc0-ef527e4f625f/url?iterationId=31f38b48-43e6-43ff-a3c3-4638be6d0f52"
        const val TEST_IMG_URL = "https://www.ifauna.cz/images/nforum-foto/prew/201204/4f998f80e6b2b.jpg"
        const val AZURE_VIEWREC_FILE = "" // not used yet
        fun body (st: String): String {
            return "{\"Url\": \"" + st +  " \"}"
        }
        const val sampleReturnJson = "{\n" +
                "\t\"Id\":\"7cf85d90-e279-47aa-bace-733db544ae61\",\n" +
                "\t\"Project\":\"6ed4e03b-5c8d-4cc1-9cc0-ef527e4f625f\",\n" +
                "\t\"Iteration\":\"31f38b48-43e6-43ff-a3c3-4638be6d0f52\",\n" +
                "\t\"Created\":\"2018-04-06T21:31:43.3766604Z\",\n" +
                "\t\"Predictions\":[{\n" +
                "\t\t\"TagId\":\"ebd33ac5-914c-47ac-857f-1c536c41e68c\",\n" +
                "\t\t\"Tag\":\"egg_mid\",\"Probability\":0.03388239\n" +
                "\t},{\n" +
                "\t\t\"TagId\":\"e0441825-09ee-4cbd-833e-6bf6bd45e610\",\n" +
                "\t\t\"Tag\":\"egg_early\",\"Probability\":0.00167871953\n" +
                "\t}]\n" +
                "}"
    }

    fun dispatch(link: String) {



        Eulog.w("yes_dispatch")
        println("YES_dispatch")
        var client = OkHttpClient();
        var mediaType= MediaType.parse("application/json");
        var body = RequestBody.create(mediaType, body(link));
        var request = Request.Builder()
                .url(AZURE_VIEWREC_URL)
                .post(body)
                .addHeader("prediction-key", "4e26554b300f47478e5c880d2a6492d7")
                .addHeader("content-type", "application/json")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = callBacks.onAzureFailure()
            override fun onResponse(call: Call, response: Response){
                val str = response.body()?.string()
                if (str != null && response.code() == 200  ) {
                    println("Success" + str);
                    callBacks.onAzureSuccess(str)
                } else {
                    callBacks.onAzureFailure()
                }
            }
        })


    }





}
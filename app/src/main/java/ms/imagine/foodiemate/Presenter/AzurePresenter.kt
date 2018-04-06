package ms.imagine.foodiemate.Presenter

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import ms.imagine.foodiemate.activity.BaseActivity

import ms.imagine.foodiemate.callbacks.AzureCallBacks

class AzurePresenter(val context: BaseActivity , val azureCallbacks: AzureCallBacks) {
    companion object {
        const val AZURE_VIEWREC_URL = "https://southcentralus.api.cognitive.microsoft.com/customvision/v1.1/Prediction/6ed4e03b-5c8d-4cc1-9cc0-ef527e4f625f/url?iterationId=31f38b48-43e6-43ff-a3c3-4638be6d0f52"
        const val AZURE_VIEWREC_FILE = "" // not used yet
    }

    fun cool (){

    }

    fun azureEggRecon(url: String){
        // val textView = null;        // destination of data

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.POST, AZURE_VIEWREC_URL,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    context.toast("Response is: ${response.substring(0, 500)}")
                },
                Response.ErrorListener { context.toast ("That didn't work!") })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}
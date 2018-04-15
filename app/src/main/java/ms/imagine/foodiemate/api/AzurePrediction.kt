package ms.imagine.foodiemate.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AzurePrediction {
    @SerializedName("Id")
    @Expose
    var id: String? = null
    @SerializedName("Project")
    @Expose
    var project: String? = null
    @SerializedName("Iteration")
    @Expose
    var iteration: String? = null
    @SerializedName("Created")
    @Expose
    var created: String? = null
    @SerializedName("Predictions")
    @Expose
    var predictions: List<Prediction>? = null

    override fun toString(): String {
        return id + project + iteration + created
    }
}

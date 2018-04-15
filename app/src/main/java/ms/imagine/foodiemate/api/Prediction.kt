package ms.imagine.foodiemate.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Prediction {
    @SerializedName("TagId")
    @Expose
    var tagId: String? = null
    @SerializedName("Tag")
    @Expose
    var tag: String? = null
    @SerializedName("Probability")
    @Expose
    var probability: Double? = null

}
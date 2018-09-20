package ms.imagine.foodiemate.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prediction {
    @SerializedName("TagId")
    @Expose
    public String tagId;

    @SerializedName("Tag")
    @Expose
    public String tag;

    @SerializedName("Probability")
    @Expose
    public Double probability;

}
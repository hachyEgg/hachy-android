package ms.imagine.foodiemate.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prediction {
    @SerializedName("TagId")
    @Expose
    private String tagId;

    @SerializedName("Tag")
    @Expose
    private String tag;

    @SerializedName("Probability")
    @Expose
    private Double probability;
}
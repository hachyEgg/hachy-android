package ms.imagine.foodiemate.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AzurePrediction {
    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("Project")
    @Expose
    private String project;

    @SerializedName("Iteration")
    @Expose
    private String iteration;

    @SerializedName("Created")
    @Expose
    private String created;

    @SerializedName("Predictions")
    @Expose
    private List<Prediction> predictions;

    @Override public String toString() {
        return id + project + iteration + created;
    }
}

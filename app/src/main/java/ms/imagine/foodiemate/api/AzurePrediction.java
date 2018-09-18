package ms.imagine.foodiemate.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AzurePrediction {
    @SerializedName("Id")
    @Expose
    public String id;

    @SerializedName("Project")
    @Expose
    public String project;

    @SerializedName("Iteration")
    @Expose
    public String iteration;

    @SerializedName("Created")
    @Expose
    public String created;

    @SerializedName("Predictions")
    @Expose
    public List<Prediction> predictions;

    @Override public String toString() {
        return id + project + iteration + created;
    }
}

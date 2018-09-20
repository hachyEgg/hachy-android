package ms.imagine.foodiemate.callbacks;

import ms.imagine.foodiemate.api.Prediction;

import java.util.List;

public interface AzureCallBacks {
    void onAzureSuccess(List<Prediction> prediction);
    void onAzureFailure(String str);
}

package ms.imagine.foodiemate.callbacks;

import android.net.Uri;

public interface StWriteCallBacks {
    void uploadFailed();
    void uploadSuccess(Uri uri);
}

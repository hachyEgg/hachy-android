package ms.imagine.foodiemate.callbacks;

import android.net.Uri;

public interface StReadCallBacks {
    void downloadFailed();
    void downloadSuccess(Uri uri);
}

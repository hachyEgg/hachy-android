package ms.imagine.foodiemate.callbacks;

import ms.imagine.foodiemate.data.Eggs;

public interface Database {
    void retrieveEgg(String key, Eggs egg);
}

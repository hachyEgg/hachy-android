package ms.imagine.foodiemate.callbacks;

import ms.imagine.foodiemate.data.Eggs;

public interface DbReadCallBacks {
    void retrieveEgg(String key, Eggs egg);
    void retrieveEggError();
}

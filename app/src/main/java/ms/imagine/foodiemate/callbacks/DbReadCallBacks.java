package ms.imagine.foodiemate.callbacks;

import ms.imagine.foodiemate.data.Egg;

public interface DbReadCallBacks {
    void retrieveEgg(String key, Egg egg);
    void retrieveEggError();
}

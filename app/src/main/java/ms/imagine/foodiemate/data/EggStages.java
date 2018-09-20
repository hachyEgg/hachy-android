package ms.imagine.foodiemate.data;

import android.util.Log;
import ms.imagine.foodiemate.api.Prediction;
import org.json.JSONObject;

import java.util.List;

public class EggStages {
    double[] stage = new double[]{.0, .1, .0, 0.1};
    List<Prediction> prediction;
    private static final String TAG = "EGG_STAGE";
    private static double IS_EGG = 0.80;

    public EggStages(List<Prediction> prediction){
        attempt_toConvert();
    }

    public void attempt_toConvert(){

        for (Prediction i:  prediction){
            String paramTag = i.tag;
            Double paramPredicability = i.probability;
            if (paramTag == null || paramPredicability == null){
                continue;
            } else {
                if (paramTag.equals("egg") ) {
                    stage[0] = paramPredicability;
                } else if (paramTag.equals("egg_mid") ) {
                    stage[2] = paramPredicability;
                } else if (paramTag.equals("egg_early")){
                    stage[1] = paramPredicability;
                } else if (paramTag.equals("egg_late")){
                    stage[3] = paramPredicability;
                }
            }
        }
        Log.d(TAG, "" + stage[0]+"," + stage[1] +","+ stage[2]+"," + stage[3]);
    }

    boolean isEgg() {return (stage[0]>IS_EGG);}

    public int waEgg() {
        int i = 0;
        double tempStore = 0.0;
        if (!isEgg() ){return 0;}
        for (int index = 0; i < stage.length; i++){
            if(index !=0 && stage[index] > tempStore) {
                tempStore = stage[index];
                i = index;
            }
        }
        return i;
    }
}
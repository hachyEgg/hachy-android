package ms.imagine.foodiemate.data;
/**
 * Created by eugen on 3/30/2018.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;


@Data
@AllArgsConstructor
public class Egg implements Serializable{
    private String remoteImgURL;
    private int status;
    private long timestamp;

    public Egg(){}
}

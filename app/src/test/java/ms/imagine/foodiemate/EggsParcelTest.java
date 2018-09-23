package ms.imagine.foodiemate;

import android.net.Uri;
import android.os.Parcel;
import ms.imagine.foodiemate.data.Egg;
import ms.imagine.foodiemate.data.Eggs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
public class EggsParcelTest {
    Eggs o;
    ArrayList<Egg> list;

    public EggsParcelTest(){
        list = new ArrayList<>();
        list.add( new Egg("xd", 1, 1));
        o = new Eggs("a", System.currentTimeMillis(), 0, "google.com", Uri.EMPTY, false, list);

    }

    @Test
    public void isValidEggs(){
        System.out.println(o);

        assert (o instanceof  Eggs);
    }

    @Test
    public void isParcelable() {
        String a = o.toString();
        Parcel parcel = Parcel.obtain();
        assert (parcel!=null);
        o.writeToParcel(parcel, o.describeContents());
        parcel.setDataPosition(0);
        Eggs newEgg = new Eggs(parcel);
        String b = newEgg.toString();
        System.out.println(a);
        System.out.println(b);
        assert(a.equals(b));
    }
}

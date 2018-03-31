package ms.imagine.foodiemate.Presenter;

import android.content.Context;

import ms.imagine.foodiemate.views.ILoginView;

/**
 * Created by eugen on 3/27/2018.
 */

public class FacebookOauthPresenter implements IFacebookOauthPresenter{
    ILoginView mILoginView;
    Context mContext;

    FacebookOauthPresenter(Context context, ILoginView view){
        mILoginView = view;
        mContext = context;
    }


}

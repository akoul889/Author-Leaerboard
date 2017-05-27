package com.quintype.autholeaderboards.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;

public class BaseFragment extends Fragment {

    public FragmentCallbacks fragmentCallbacks;
    Activity mActivity;

    /*
     * onAttach(Context) is not called on pre API 23 versions of Android and onAttach(Activity) is deprecated
     * Use onAttachToContext instead
     */
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /*
     * Deprecated on API 23
     * Use onAttachToContext instead
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
        mActivity = activity;
    }

    /*
     * Called when the fragment attaches to the context
     */
    protected void onAttachToContext(Context context) {
        if (context instanceof FragmentCallbacks) {
            fragmentCallbacks = (FragmentCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentCallback methods");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentCallbacks = null;
    }
}

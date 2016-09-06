package me.walkonly.lib.fragment;

public class TabFragment extends BaseFragment {

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden)
            onPause();
        else
            onResume();
    }

}

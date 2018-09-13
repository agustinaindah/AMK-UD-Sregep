package com.amkuds.app.features.main;

import android.os.Bundle;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseFragment;

public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {

        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int setView() {
        return R.layout.fragment_main;
    }
}

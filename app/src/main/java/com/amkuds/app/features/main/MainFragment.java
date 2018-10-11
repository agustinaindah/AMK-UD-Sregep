package com.amkuds.app.features.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.amkuds.app.AmkUdsApp;
import com.amkuds.app.R;
import com.amkuds.app.base.BaseFragment;
import com.amkuds.app.features.form_data.FormInputDataActivity;
import com.amkuds.app.features.list_data.ListDataEmployeeActivity;
import com.amkuds.app.utils.Consts;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {

    @BindView(R.id.cardDataKaryawan)
    CardView cardDataKaryawan;
    @BindView(R.id.cardInputKaryawan)
    CardView cardInputKaryawan;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*SharedPreferences sPref = AmkUdsApp.getInstance().Prefs();
        Log.d("tes", sPref.getString(Consts.ROLE,""));*/
    }

    @Override
    protected int setView() {
        return R.layout.fragment_main;
    }

    @OnClick(R.id.cardDataKaryawan)
    public void listData(){
        Intent intent = new Intent(getActivity(), ListDataEmployeeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardInputKaryawan)
    public void inputData(){
        Intent intent = new Intent(getActivity(), FormInputDataActivity.class);
        startActivity(intent);
    }
}

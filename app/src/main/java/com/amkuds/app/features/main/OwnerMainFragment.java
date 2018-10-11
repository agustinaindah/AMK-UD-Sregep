package com.amkuds.app.features.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseFragment;
import com.amkuds.app.features.list_data.ListDataEmployeeActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class OwnerMainFragment extends BaseFragment {

    @BindView(R.id.cardDataKaryawan)
    CardView cardDataKaryawan;

    public static OwnerMainFragment newInstance() {
        Bundle args = new Bundle();
        OwnerMainFragment fragment = new OwnerMainFragment();
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
        return R.layout.fragment_owner_main;
    }

    @OnClick(R.id.cardDataKaryawan)
    public void listData(){
        Intent intent = new Intent(getActivity(), ListDataEmployeeActivity.class);
        startActivity(intent);
    }
}

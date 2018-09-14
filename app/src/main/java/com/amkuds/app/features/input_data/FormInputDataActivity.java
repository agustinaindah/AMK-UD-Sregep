package com.amkuds.app.features.input_data;

import android.os.Bundle;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;

public class FormInputDataActivity extends BaseActivity {
    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Input Data Karyawan");
    }

    @Override
    protected int setView() {
        return R.layout.activity_form_input_data_karyawan;
    }
}

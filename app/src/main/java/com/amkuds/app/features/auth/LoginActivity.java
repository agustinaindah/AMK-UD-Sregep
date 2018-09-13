package com.amkuds.app.features.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.features.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {

    }


    @Override
    protected int setView() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btnLogin)
    public void Login(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

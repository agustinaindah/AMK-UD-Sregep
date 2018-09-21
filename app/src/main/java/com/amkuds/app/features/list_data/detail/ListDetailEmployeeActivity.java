package com.amkuds.app.features.list_data.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.features.form_data.FormInputDataActivity;
import com.amkuds.app.features.form_data.edit_data.FormEditDataActivity;

import butterknife.BindView;

public class ListDetailEmployeeActivity extends BaseActivity {

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.txtNikEmp)
    TextView txtNikEmp;
    @BindView(R.id.txtNameEmp)
    TextView txtNameEmp;
    @BindView(R.id.txtTmptLahir)
    TextView txtTmptLahir;
    @BindView(R.id.txtTglLahir)
    TextView txtTglLahir;
    @BindView(R.id.txtAgamaEmp)
    TextView txtAgamaEmp;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.txtJenisKelaminEmp)
    TextView txtJenisKelaminEmp;
    @BindView(R.id.txtNoHp)
    TextView txtNoHp;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtAlamat)
    TextView txtAlamat;
    @BindView(R.id.txtAlamatKtp)
    TextView txtAlamatKtp;

    @BindView(R.id.txtNip)
    TextView txtNip;
    @BindView(R.id.txtJabatan)
    TextView txtJabatan;
    @BindView(R.id.txtStatusEmp)
    TextView txtStatusEmp;
    @BindView(R.id.txtTglMasuk)
    TextView txtTglMasuk;
    @BindView(R.id.txtTglKeluar)
    TextView txtTglKeluar;
    @BindView(R.id.txtGajiEmp)
    TextView txtGajiEmp;

    @BindView(R.id.imgFotoData)
    ImageView imgFotoData;
    @BindView(R.id.imgFile)
    ImageView imgFile;
    @BindView(R.id.textfile)
    TextView textfile;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        collapsingToolbar.setTitle("");

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(" ");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

    }

    @Override
    protected int setView() {
        return R.layout.activity_list_detail_employee;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_detail_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                intent = new Intent(this, FormEditDataActivity.class);
                break;
            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

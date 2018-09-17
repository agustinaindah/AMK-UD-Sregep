package com.amkuds.app.features.input_data;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amkuds.app.DateDialog;
import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.utils.Consts;
import com.amkuds.app.utils.Helper;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.HEAD;

public class FormInputDataActivity extends BaseActivity implements DateDialog.OnDateDialog{

    private static final int GALLERY_REQUEST = 565;

    @BindView(R.id.txtTglLahir)
    TextView txtTglLahir;
    @BindView(R.id.txtTglMasuk)
    TextView txtTglMasuk;
    @BindView(R.id.txtTglKeluar)
    TextView txtTglKeluar;
    @BindView(R.id.btnUploadFotoDiri)
    Button btnUploadFotoDiri;
    @BindView(R.id.btnUploadFotoData)
    Button btnUploadFotoData;
  /*  @BindView(R.id.btnUploadFile)
    Button btnUploadFile;*/

    private String mTglLahir;
    private String mTglMasuk;
    private String mTglKeluar;

    private String newPhoto = null;
    private GalleryPhoto galleryPhoto;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Input Data Karyawan");

        galleryPhoto = new GalleryPhoto(this);
        displayData();

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // Log.i("Tes", "Permission to record denied");
            makeRequest();
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                GALLERY_REQUEST);
    }

    @OnClick(R.id.btnUploadFotoDiri)
    public void clickUploadPhotoSelf(View view) {
        galleryIntent();
    }

    @OnClick(R.id.btnUploadFotoData)
    public void clickUploadPhotoData(View view) {
        galleryIntent();
    }

    @OnClick(R.id.btnUploadFile)
    public void clickUploadFile(View view) {
        galleryIntent();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, GALLERY_REQUEST);
        }
    }

    private void displayData() {
        mTglLahir = Helper.getDateNow();
        mTglMasuk = Helper.getDateNow();
        mTglKeluar = Helper.getDateNow();
    }

    @Override
    protected int setView() {
        return R.layout.activity_form_input_data_karyawan;
    }

    @OnClick(R.id.txtTglLahir)
    public void tglLahir(View view){
        DialogFragment dialogFragment = DateDialog.newInstance(mTglLahir, this);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @OnClick(R.id.txtTglMasuk)
    public void tglMasuk(View view){
        DialogFragment dialogFragment = DateDialog.newInstance(mTglMasuk, this);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @OnClick(R.id.txtTglKeluar)
    public void tglKeluar(View view){
        DialogFragment dialogFragment = DateDialog.newInstance(mTglKeluar, this);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @Override
    public void onSelectedDate(String selectedDate) {
        mTglLahir = selectedDate;
        mTglMasuk = selectedDate;
        mTglKeluar = selectedDate;

        txtTglLahir.setText(Helper.parseToDateString(mTglLahir, Consts.TYPE_DATE));
        txtTglMasuk.setText(Helper.parseToDateString(mTglMasuk, Consts.TYPE_DATE));
        txtTglKeluar.setText(Helper.parseToDateString(mTglKeluar, Consts.TYPE_DATE));
    }
}

package com.amkuds.app.features.form_data;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amkuds.app.DateDialog;
import com.amkuds.app.EndDateDialog;
import com.amkuds.app.StartDateDialog;
import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.utils.Consts;
import com.amkuds.app.utils.Helper;
import com.amkuds.app.utils.Utility;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class FormInputDataActivity extends BaseActivity implements StartDateDialog.StartOnDateDialog,
        EndDateDialog.EndOnDateDialog, DateDialog.OnDateDialog{

    private static final int GALLERY_REQUEST = 565;
    private static final int REQUEST_CAMERA = 456;

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
    @BindView(R.id.btnUploadFile)
    Button btnUploadFile;

    @BindString(R.string.label_take_photo)
    String strTakePhoto;
    @BindString(R.string.label_choose_gallery)
    String strChooseGallery;
    @BindString(R.string.label_cancel)
    String strCancel;
    @BindString(R.string.label_add_photo)
    String strAddPhoto;

    private String mTglLahir;
    private String mTglMasuk;
    private String mTglKeluar;

    private String newPhoto = null;
    private GalleryPhoto galleryPhoto;
    private String userChoosenTask;

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
        final CharSequence[] items = {strTakePhoto, strChooseGallery,
                strCancel};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormInputDataActivity.this);
        builder.setTitle(strAddPhoto);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(FormInputDataActivity.this);
                if (items[item].equals(strTakePhoto)) {
                    userChoosenTask = strTakePhoto;
                    if (result)
                        cameraIntent();
                } else if (items[item].equals(strChooseGallery)) {
                    userChoosenTask = strChooseGallery;
                    if (result)
                        galleryIntent();
                } else if (items[item].equals(strCancel)) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @OnClick(R.id.btnUploadFotoData)
    public void clickUploadPhotoData(View view) {
        final CharSequence[] items = {strTakePhoto, strChooseGallery,
                strCancel};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormInputDataActivity.this);
        builder.setTitle(strAddPhoto);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(FormInputDataActivity.this);
                if (items[item].equals(strTakePhoto)) {
                    userChoosenTask = strTakePhoto;
                    if (result)
                        cameraIntent();
                } else if (items[item].equals(strChooseGallery)) {
                    userChoosenTask = strChooseGallery;
                    if (result)
                        galleryIntent();
                } else if (items[item].equals(strCancel)) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @OnClick(R.id.btnUploadFile)
    public void clickUploadFile(View view) {
        final CharSequence[] items = {strTakePhoto, strChooseGallery,
                strCancel};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormInputDataActivity.this);
        builder.setTitle(strAddPhoto);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(FormInputDataActivity.this);
                if (items[item].equals(strTakePhoto)) {
                    userChoosenTask = strTakePhoto;
                    if (result)
                        cameraIntent();
                } else if (items[item].equals(strChooseGallery)) {
                    userChoosenTask = strChooseGallery;
                    if (result)
                        galleryIntent();
                } else if (items[item].equals(strCancel)) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, GALLERY_REQUEST);
        }
    }

    private void cameraIntent() {
       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);*/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
        startActivityForResult(intent, REQUEST_CAMERA);
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
        DialogFragment dialogFragment = StartDateDialog.newInstance(mTglMasuk, this);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @OnClick(R.id.txtTglKeluar)
    public void tglKeluar(View view){
        DialogFragment dialogFragment = EndDateDialog.newInstance(mTglKeluar, this);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @Override
    public void onStartSelectedDate(String startDate) {
        mTglMasuk = startDate;
        txtTglMasuk.setText(Helper.parseToDateString(mTglMasuk, Consts.TYPE_DATE));
    }

    @Override
    public void onEndSelectedDate(String EndDate) {
        mTglKeluar = EndDate;
        txtTglKeluar.setText(Helper.parseToDateString(mTglKeluar, Consts.TYPE_DATE));
    }

    @Override
    public void onSelectedDate(String onDate) {
        mTglLahir = onDate;
        txtTglLahir.setText(Helper.parseToDateString(mTglLahir, Consts.TYPE_DATE));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        /*galleryPhoto.setPhotoUri(data.getData());
        String photoPath = galleryPhoto.getPath();
        try {
            Bitmap bitmap = ImageLoader
                    .init()
                    .from(photoPath)
                    .requestSize(512, 512)
                    .getBitmap();
            imgFormOrder.setImageBitmap(bitmap);
            newPhoto = photoPath;
            if (newPhoto != null) {
                try {
                    ImageLoader imgLoader = ImageLoader
                            .init()
                            .from(newPhoto);
                    Bitmap newPhoto = imgLoader.requestSize(130, 130).getBitmap();
                    fileEvidence = ImageBase64.encode(newPhoto);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    private void onCaptureImageResult(Intent data) {

        /*Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, null, null);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte [] newImage = bytes.toByteArray();

        imgFormOrder.setImageBitmap(thumbnail);

        fileEvidence = Base64.encodeToString(newImage, Base64.DEFAULT);*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals(strTakePhoto))
                        cameraIntent();
                    else if (userChoosenTask.equals(strChooseGallery))
                        galleryIntent();
                } else {
                    //code for deny
                    Log.i("Tes", "Permission has been granted by user");
                }
                break;
        }
    }
}

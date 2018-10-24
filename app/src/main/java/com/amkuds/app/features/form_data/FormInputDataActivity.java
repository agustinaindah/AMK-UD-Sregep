package com.amkuds.app.features.form_data;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amkuds.app.AmkUdsApp;
import com.amkuds.app.features.date_dialog.DateDialog;
import com.amkuds.app.features.date_dialog.EndDateDialog;
import com.amkuds.app.features.date_dialog.StartDateDialog;
import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.features.form_data.edit_data.FormEditDataActivity;
import com.amkuds.app.utils.CallbackInterface;
import com.amkuds.app.utils.Consts;
import com.amkuds.app.utils.Helper;
import com.amkuds.app.utils.Utility;
import com.google.gson.JsonObject;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class FormInputDataActivity extends BaseActivity implements StartDateDialog.StartOnDateDialog,
        DateDialog.OnDateDialog, EndDateDialog.EndOnDateDialog, FormInputDataPresenter.View {

    private static final int PICKFILE_RESULT_CODE = 1;
    private static final int GALLERY_REQUEST = 565;
    private static final int REQUEST_CAMERA = 456;
    private static final int GALLERY_REQUEST2 = 600;
    private static final int REQUEST_CAMERA2 = 620;

    @BindView(R.id.edtNikEmp)
    EditText edtNikEmp;
    @BindView(R.id.edtNamaLengkap)
    EditText edtNamaLengkap;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.rbGenderMale)
    RadioButton rbGenderMale;
    @BindView(R.id.rbGenderFemale)
    RadioButton rbGenderFemale;
    @BindView(R.id.edtTmptLahir)
    EditText edtTmptLahir;
    @BindView(R.id.txtTglLahir)
    TextView txtTglLahir;
    /*@BindView(R.id.edtAgama)
    EditText edtAgama;*/
    @BindView(R.id.spinReligion)
    Spinner spinReligion;
    @BindView(R.id.edtAlamat)
    EditText edtAlamat;
    @BindView(R.id.edtAlamatKtp)
    EditText edtAlamatKtp;
    @BindView(R.id.edtNoHp)
    EditText edtNoHp;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.rgStatus)
    RadioGroup rgStatus;
    @BindView(R.id.rbStatusBlmKawin)
    RadioButton rbStatusBlmKawin;
    @BindView(R.id.rbStatusKawin)
    RadioButton rbStatusKawin;

    @BindView(R.id.edtNip)
    EditText edtNip;
    @BindView(R.id.edtPosition)
    EditText edtPosition;
    @BindView(R.id.spinStatusKary)
    Spinner spinStatusKary;
    @BindView(R.id.edtSalary)
    EditText edtSalary;
    @BindView(R.id.txtTglMasuk)
    TextView txtTglMasuk;
     @BindView(R.id.txtTglKeluar)
     TextView txtTglKeluar;
   /* @BindView(R.id.textfile)
    TextView textfile;*/
    @BindView(R.id.imgFotoDiri)
    ImageView imgFotoDiri;
    @BindView(R.id.btnUploadFotoDiri)
    Button btnUploadFotoDiri;
    @BindView(R.id.imgFotoData)
    ImageView imgFotoData;
    @BindView(R.id.btnUploadFotoData)
    Button btnUploadFotoData;
   /* @BindView(R.id.btnUploadFile)
    Button btnUploadFile;*/

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindString(R.string.label_take_photo)
    String strTakePhoto;
    @BindString(R.string.label_choose_gallery)
    String strChooseGallery;
    @BindString(R.string.label_cancel)
    String strCancel;
    @BindString(R.string.label_add_photo)
    String strAddPhoto;

    @BindString(R.string.info)
    String strInfo;
    @BindString(R.string.msg_confirmed)
    String strSuccessConfirm;
    @BindString(R.string.msg_empty)
    String strMsgEmpty;

    private String mTglLahir;
    private String mTglMasuk;
    private String mTglKeluar;

    private String fileEvidence;
    private String fileEvidence2;
    private String newPhoto = null;
    private String newPhoto2 = null;
    private GalleryPhoto galleryPhoto;
//    private GalleryPhoto galleryPhoto2;
    private String userChoosenTask;
    private String FileData = null;

    private ProgressDialog progressDialog;
    private FormInputDataPresenter mPresenter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Input Data Karyawan");

        mPresenter = new FormInputDataPresenterImpl(this);
        galleryPhoto = new GalleryPhoto(this);
//        galleryPhoto2 = new GalleryPhoto(this);
        displayData();
        initProgress();

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // Log.i("Tes", "Permission to record denied");
            makeRequest();
        }
    }

    private void initProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                GALLERY_REQUEST);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, GALLERY_REQUEST);
        }
    }

    private void galleryIntent2() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, GALLERY_REQUEST2);
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

    private void cameraIntent2() {
       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);*/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
        startActivityForResult(intent, REQUEST_CAMERA2);
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
                        cameraIntent2();
                } else if (items[item].equals(strChooseGallery)) {
                    userChoosenTask = strChooseGallery;
                    if (result)
                        galleryIntent2();
                } else if (items[item].equals(strCancel)) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

     /* @OnClick(R.id.btnUploadFile)
    public void clickUploadFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, PICKFILE_RESULT_CODE);
    }*/

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
    public void tglLahir(View view) {
        DialogFragment dialogFragment = DateDialog.newInstance(mTglLahir, this);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @OnClick(R.id.txtTglMasuk)
    public void tglMasuk(View view) {
        DialogFragment dialogFragment = StartDateDialog.newInstance(mTglMasuk, this);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @OnClick(R.id.txtTglKeluar)
    public void tglKeluar(View view) {
        DialogFragment dialogFragment = EndDateDialog.newInstance(mTglKeluar, this);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @Override
    public void onSelectedDate(String onDate) {
        mTglLahir = onDate;
        txtTglLahir.setText(Helper.parseToDateString(mTglLahir, Consts.TYPE_DATE));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    onSelectFromGalleryResult(data);
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    onCaptureImageResult(data);
                }
                break;
            case GALLERY_REQUEST2:
                if (resultCode == RESULT_OK) {
                    onSelectFromGalleryResult2(data);
                }
                break;
            case REQUEST_CAMERA2:
                if (resultCode == RESULT_OK) {
                    onCaptureImageResult2(data);
                }
                break;
          /*  case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {

                    String FilePath = data.getData().getPath();
                    textfile.setText(FilePath);

                    File file = new File(FilePath);
                    byte[] bytesArray = file.to;

                    FileData = Base64.encodeToString(bytesArray, Base64.DEFAULT);
                    Log.d("tesF", FileData);

                    byte [] data;
                    try{
                        File file = new File(data.getData().getPath());
                        FileInputStream fis = new FileInputStream(file);
                        byte [] data = new byte[(int)file.length()];
                        fis.read(data);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        data = bos.toByteArray();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    //convert file to byte[]
//                    File file = new File(FilePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try{
                        ObjectOutputStream oos = new ObjectOutputStream(bos);
                        oos = new ObjectOutputStream(bos);
                        oos.writeObject(file);
                        bos.close();
                        oos.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    byte[] bytearray = bos.toByteArray();
                    long byteLength = file.length();

                    byte[] filecontent = new byte[(int) byteLength];

//                    FileData = Base64.encodeToString(filecontent, Base64.DEFAULT);

                }
                break;*/
        }
    }

    @OnClick(R.id.btnSubmit)
    public void submit(View view) {
        if (!validate()) {
        } if (fileEvidence == null || fileEvidence2 == null) {
            Toast.makeText(this, "Foto diri dan foto data harus diisi", Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.postInputEmployee(getInput());
        }
    }

    private JsonObject getInput() {
        JsonObject jsonInput = new JsonObject();
        try {
            SharedPreferences mPref = AmkUdsApp.getInstance().Prefs();
            Log.d("id user", String.valueOf(mPref.getInt(Consts.ID, 0)));
            String gender = (rgGender.getCheckedRadioButtonId() == R.id.rbGenderFemale) ? "0" : "1";
            String status = (rgStatus.getCheckedRadioButtonId() == R.id.rbStatusBlmKawin) ? "0" : "1";

            jsonInput.addProperty("id_user", mPref.getInt(Consts.ID, 0));
            jsonInput.addProperty("nama", edtNamaLengkap.getText().toString());
            jsonInput.addProperty("jk", gender);
            jsonInput.addProperty("nip", edtNip.getText().toString());
            jsonInput.addProperty("no_ktp", edtNikEmp.getText().toString());
            jsonInput.addProperty("no_hp", edtNoHp.getText().toString());
            jsonInput.addProperty("email", edtEmail.getText().toString());
            jsonInput.addProperty("tgl_masuk", mTglMasuk);
            jsonInput.addProperty("alamat", edtAlamat.getText().toString());
            jsonInput.addProperty("tgl_lahir", mTglLahir);
            jsonInput.addProperty("tempat_kelahiran", edtTmptLahir.getText().toString());
            jsonInput.addProperty("domisili", edtAlamatKtp.getText().toString());
            jsonInput.addProperty("jabatan", edtPosition.getText().toString());
            jsonInput.addProperty("foto", fileEvidence);
            jsonInput.addProperty("foto_ktp", fileEvidence2);
            jsonInput.addProperty("doc", "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");
            jsonInput.addProperty("status", status);
            jsonInput.addProperty("status_karyawan", spinStatusKary.getSelectedItem().toString().toLowerCase());
            jsonInput.addProperty("agama", spinReligion.getSelectedItem().toString().toLowerCase());
            jsonInput.addProperty("tgl_selesai", mTglKeluar);
            jsonInput.addProperty("salary", edtSalary.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    public boolean validate() {
        edtNamaLengkap.setError(null);
        edtNip.setError(null);
        edtNikEmp.setError(null);
        edtNoHp.setError(null);
        edtEmail.setError(null);
        edtAlamat.setError(null);
        edtTmptLahir.setError(null);
        edtAlamatKtp.setError(null);
        edtPosition.setError(null);
        edtSalary.setError(null);

        boolean cancel = false;
        View focus = null;

        if (TextUtils.isEmpty(getInput().get("nama").getAsString())) {
            edtNamaLengkap.setError(strMsgEmpty);
            focus = edtNamaLengkap;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("nip").getAsString())) {
            edtNip.setError(strMsgEmpty);
            focus = edtNip;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("no_ktp").getAsString())) {
            edtNikEmp.setError(strMsgEmpty);
            focus = edtNikEmp;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("no_hp").getAsString())) {
            edtNoHp.setError(strMsgEmpty);
            focus = edtNoHp;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("email").getAsString())) {
            edtEmail.setError(strMsgEmpty);
            focus = edtEmail;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("alamat").getAsString())) {
            edtAlamat.setError(strMsgEmpty);
            focus = edtAlamat;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("tempat_kelahiran").getAsString())) {
            edtTmptLahir.setError(strMsgEmpty);
            focus = edtTmptLahir;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("domisili").getAsString())) {
            edtAlamatKtp.setError(strMsgEmpty);
            focus = edtAlamatKtp;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("jabatan").getAsString())) {
            edtPosition.setError(strMsgEmpty);
            focus = edtPosition;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("salary").getAsString())) {
            edtSalary.setError(strMsgEmpty);
            focus = edtSalary;
            cancel = true;
        }
        if (cancel) {
            focus.requestFocus();
        }
        return cancel;
    }


    private void onSelectFromGalleryResult(Intent data) {
        galleryPhoto.setPhotoUri(data.getData());
        String photoPath = galleryPhoto.getPath();
        try {
            Bitmap bitmap = ImageLoader
                    .init()
                    .from(photoPath)
                    .requestSize(512, 512)
                    .getBitmap();
            imgFotoDiri.setImageBitmap(bitmap);
            newPhoto = photoPath;
            if (newPhoto != null) {
                try {
                    ImageLoader imgLoader = ImageLoader
                            .init()
                            .from(newPhoto);
                    Bitmap newPhoto = imgLoader.requestSize(1024, 1024).getBitmap();
                    fileEvidence = ImageBase64.encode(newPhoto);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void onSelectFromGalleryResult2(Intent data) {
        galleryPhoto.setPhotoUri(data.getData());
        String photoPath = galleryPhoto.getPath();
        try {
            Bitmap bitmap = ImageLoader
                    .init()
                    .from(photoPath)
                    .requestSize(512, 512)
                    .getBitmap();
            imgFotoData.setImageBitmap(bitmap);
            newPhoto2 = photoPath;
            if (newPhoto2 != null) {
                try {
                    ImageLoader imgLoader = ImageLoader
                            .init()
                            .from(newPhoto);
                    Bitmap newPhoto2 = imgLoader.requestSize(1024, 1024).getBitmap();
                    fileEvidence2 = ImageBase64.encode(newPhoto2);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, null, null);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] newImage = bytes.toByteArray();

        imgFotoDiri.setImageBitmap(thumbnail);

        fileEvidence = Base64.encodeToString(newImage, Base64.DEFAULT);
    }

    private void onCaptureImageResult2(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, null, null);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] newImage = bytes.toByteArray();

        imgFotoData.setImageBitmap(thumbnail);

        fileEvidence2 = Base64.encodeToString(newImage, Base64.DEFAULT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals(strTakePhoto)) {
                        cameraIntent();
                        cameraIntent2();
                    } else if (userChoosenTask.equals(strChooseGallery)) {
                        galleryIntent();
                        galleryIntent2();
                    }
                } else {
                    //code for deny
                    Log.i("Tes", "Permission has been granted by user");
                }
                break;
        }
    }

    @Override
    public void success() {
        Helper.createAlert(this, strInfo, strSuccessConfirm, false, new CallbackInterface() {
            @Override
            public void callback() {
                finish();
            }
        });
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Helper.createAlert(this, strInfo, msg);
    }

    @Override
    public void notConnect(String msg) {
        Helper.createAlert(this, strInfo, "Tidak ada jaringan");
    }
}
//        Log.d("fotoah", "validate: "+getInput().get("foto"));
        /*if (getInput().get("foto") != null || !getInput().get("foto").isJsonNull() || !getInput().get("foto").equals("null") ||
                getInput().get("foto_ktp") != null || !getInput().get("foto_ktp").isJsonNull() || !getInput().get("foto_ktp").equals("null")){
            Toast.makeText(this, "Foto Diri dan Foto KTP harus diisi", Toast.LENGTH_SHORT).show();
            focus = imgFotoDiri;
            focus = imgFotoData;
            cancel = true;
        }*/
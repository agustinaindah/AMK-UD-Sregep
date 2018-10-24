package com.amkuds.app.features.list_data.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amkuds.app.AmkUdsApp;
import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.features.form_data.edit_data.FormEditDataActivity;
import com.amkuds.app.features.list_data.ListDataEmployeeActivity;
import com.amkuds.app.model.ItemKaryawan;
import com.amkuds.app.utils.CallbackInterface;
import com.amkuds.app.utils.Consts;
import com.amkuds.app.utils.Helper;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ListDetailEmployeeActivity extends BaseActivity implements
        ListDetailEmployeePresenter.View {

    private final int REQUEST_CODE = 20;


    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.imgDetailEmployee)
    CircularImageView imgDetailEmployee;

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
    @BindView(R.id.txtTglHabisKontrak)
    TextView txtTglHabisKontrak;
    @BindView(R.id.txtGajiEmp)
    TextView txtGajiEmp;

    @BindView(R.id.imgFotoDiri)
    ImageView imgFotoDiri;
    @BindView(R.id.imgFotoData)
    ImageView imgFotoData;
   /* @BindView(R.id.textfile)
    TextView textfile;*/

    @BindView(R.id.layProgress)
    RelativeLayout layProgress;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;

    private ListDetailEmployeePresenter mPresenter;
    private int id;
    private ItemKaryawan mItemKaryawan;
    private ItemKaryawan UItemKaryawan;
    private Fragment mFragment = null;
    private FragmentManager fm;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        mPresenter = new ListDetailEmployeePresenterImpl(this);
        initData();
        initLoading();

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

        displayData();
        popupFotoCircular();
        popupFotoDiri();
        popupFotoData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getSingleList(id);
    }

    private void initLoading() {
        layProgress.setVisibility(View.VISIBLE);
    }

    private void displayData() {
        Helper.displayImage(this, mItemKaryawan.getFoto(), imgDetailEmployee, true);
        Helper.displayImage(this, mItemKaryawan.getFoto(), imgFotoDiri, true);
        Helper.displayImage(this, mItemKaryawan.getFotoKtp(), imgFotoData, true);
        txtNikEmp.setText(mItemKaryawan.getNoKtp());
        txtNameEmp.setText(Helper.capitalize(mItemKaryawan.getNama()));

       /* if (mItemKaryawan.getTempatKelahiran() == null){
            txtTmptLahir.setText("-");
        } else{
            txtTmptLahir.setText(mItemKaryawan.getTempatKelahiran());
        }*/

        txtTmptLahir.setText(Helper.capitalize(mItemKaryawan.getTempatKelahiran()));
        txtTglLahir.setText(Helper.parseToDateString(mItemKaryawan.getTglLahir(), Consts.TYPE_DATE));
        txtAgamaEmp.setText(Helper.capitalize(mItemKaryawan.getAgama()));

        if (mItemKaryawan.getStatus().equals("0")) {
            txtStatus.setText("Belum Menikah");
        } else {
            txtStatus.setText("Menikah");
        }

        if (mItemKaryawan.getJk().equals("0")) {
            txtJenisKelaminEmp.setText("Perempuan");
        } else {
            txtJenisKelaminEmp.setText("Laki-laki");
        }

        txtNoHp.setText(mItemKaryawan.getNoHp());
        txtEmail.setText(mItemKaryawan.getEmail());
        txtAlamat.setText(Helper.capitalize(mItemKaryawan.getAlamat()));
        txtAlamatKtp.setText(Helper.capitalize(mItemKaryawan.getDomisili()));

        txtNip.setText(mItemKaryawan.getNip());
        txtJabatan.setText(Helper.capitalize(mItemKaryawan.getJabatan()));
        txtStatusEmp.setText(Helper.capitalize(mItemKaryawan.getStatusKaryawan()));

        if (mItemKaryawan.getTglMasuk() == null) {
            txtTglMasuk.setText("-");
        } else {
            txtTglMasuk.setText(Helper.parseToDateString(mItemKaryawan.getTglMasuk(), Consts.TYPE_DATE));
        }

        if (mItemKaryawan.getLogKontrak() == null) {
            txtTglHabisKontrak.setText("-");
        } else {
            txtTglHabisKontrak.setText(Helper.parseToDateString(mItemKaryawan.getLogKontrak(), Consts.TYPE_DATE));
        }


        txtGajiEmp.setText("Rp " + Helper.numberFormat(Integer.valueOf(mItemKaryawan.getLogSalary())));
//        textfile.setText(mItemKaryawan.getDoc());
    }

    private void initData() {
        id = getIntent().getIntExtra(Consts.ARG_ID, 0);
        mItemKaryawan = (ItemKaryawan) getIntent().getSerializableExtra(Consts.ARG_DATA);
        mPresenter.getSingleList(id);
    }

    private void popupFotoCircular() {
        final ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(800); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setBackgroundColor(Color.BLACK);  // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional

        imagePopup.initiatePopupWithPicasso(mItemKaryawan.getFoto());
        imgDetailEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.viewPopup();
            }
        });
    }

    private void popupFotoDiri() {
        final ImagePopup imagePopup2 = new ImagePopup(this);
        imagePopup2.setWindowHeight(800); // Optional
        imagePopup2.setWindowWidth(800); // Optional
        imagePopup2.setBackgroundColor(Color.BLACK);  // Optional
        imagePopup2.setFullScreen(true); // Optional
        imagePopup2.setHideCloseIcon(true);  // Optional
        imagePopup2.setImageOnClickClose(true);  // Optional

        imagePopup2.initiatePopupWithPicasso(mItemKaryawan.getFoto());
        imgFotoDiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup2.viewPopup();
            }
        });
    }

    private void popupFotoData() {
        final ImagePopup imagePopup3 = new ImagePopup(this);
        imagePopup3.setWindowHeight(800); // Optional
        imagePopup3.setWindowWidth(800); // Optional
        imagePopup3.setBackgroundColor(Color.BLACK);  // Optional
        imagePopup3.setFullScreen(true); // Optional
        imagePopup3.setHideCloseIcon(true);  // Optional
        imagePopup3.setImageOnClickClose(true);  // Optional

        imagePopup3.initiatePopupWithPicasso(mItemKaryawan.getFotoKtp());
        imgFotoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup3.viewPopup();
            }
        });
    }

    @Override
    protected int setView() {
        return R.layout.activity_list_detail_employee;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_detail_employee, menu);
        MenuItem shareItem = menu.findItem(R.id.action_edit_profile);

        // show the button when some condition is true
        SharedPreferences sPref = AmkUdsApp.getInstance().Prefs();
        String role = sPref.getString(Consts.ROLE, "");
        if (role.equalsIgnoreCase("owner")) {
            shareItem.setVisible(false);
        } else {
            shareItem.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                intent = new Intent(this, FormEditDataActivity.class);
                intent.putExtra(Consts.ARG_ID, id);
                intent.putExtra(Consts.ARG_DATA, mItemKaryawan);
                break;
            default:
                break;
        }
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE);
        }
        if (mFragment != null) {
            gotoFragment(fm, mFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(ItemKaryawan itemKaryawan) {
        mItemKaryawan = itemKaryawan;
    }

    @Override
    public void showProgress() {
        layProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        layProgress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Helper.createAlert(this, "Info", msg, true, new CallbackInterface() {
            @Override
            public void callback() {
                mPresenter.getSingleList(id);
            }
        });
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnError)
    public void reload() {
        layError.setVisibility(View.GONE);
        mPresenter.getSingleList(id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                // Extract name value from result extras
                UItemKaryawan = (ItemKaryawan) data.getSerializableExtra(Consts.ARG_DATA);

                if (UItemKaryawan.getFoto() == null) {
                    Helper.displayImage(this, mItemKaryawan.getFoto(), imgDetailEmployee, true);
                } else {
                    Log.d("tes", UItemKaryawan.getFoto());
                    byte[] decodedString = Base64.decode(UItemKaryawan.getFoto(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgDetailEmployee.setImageBitmap(decodedByte);
                }

                if (UItemKaryawan.getFoto() == null) {
                    Helper.displayImage(this, mItemKaryawan.getFoto(), imgFotoDiri, true);
                } else {
                    Log.d("tes", UItemKaryawan.getFoto());
                    byte[] decodedString = Base64.decode(UItemKaryawan.getFoto(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgFotoDiri.setImageBitmap(decodedByte);
                }

                if (UItemKaryawan.getFotoKtp() == null) {
                    Helper.displayImage(this, mItemKaryawan.getFotoKtp(), imgFotoData, true);
                } else {
                    byte[] decodedString = Base64.decode(UItemKaryawan.getFotoKtp(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgFotoData.setImageBitmap(decodedByte);
                }

                txtNikEmp.setText(UItemKaryawan.getNoKtp());
                txtNameEmp.setText(Helper.capitalize(UItemKaryawan.getNama()));
                txtTmptLahir.setText(Helper.capitalize(UItemKaryawan.getTempatKelahiran()));
                txtTglLahir.setText(Helper.parseToDateString(UItemKaryawan.getTglLahir(), Consts.TYPE_DATE));
                txtAgamaEmp.setText(Helper.capitalize(UItemKaryawan.getAgama()));

                if (UItemKaryawan.getStatus().equals("0")) {
                    txtStatus.setText("Belum Menikah");
                } else {
                    txtStatus.setText("Menikah");
                }

                if (UItemKaryawan.getJk().equals("0")) {
                    txtJenisKelaminEmp.setText("Perempuan");
                } else {
                    txtJenisKelaminEmp.setText("Laki-laki");
                }

                txtNoHp.setText(UItemKaryawan.getNoHp());
                txtEmail.setText(UItemKaryawan.getEmail());
                txtAlamat.setText(Helper.capitalize(UItemKaryawan.getAlamat()));
                txtAlamatKtp.setText(Helper.capitalize(UItemKaryawan.getDomisili()));

                txtNip.setText(UItemKaryawan.getNip());
                txtJabatan.setText(Helper.capitalize(UItemKaryawan.getJabatan()));
                txtStatusEmp.setText(Helper.capitalize(UItemKaryawan.getStatusKaryawan()));

                if (UItemKaryawan.getTglMasuk() == null) {
                    txtTglMasuk.setText("-");
                } else {
                    txtTglMasuk.setText(Helper.parseToDateString(UItemKaryawan.getTglMasuk(), Consts.TYPE_DATE));
                }

                if (UItemKaryawan.getLogKontrak().equals("")) {
                    txtTglHabisKontrak.setText(Helper.parseToDateString(mItemKaryawan.getLogKontrak(), Consts.TYPE_DATE));
                } else {
                    txtTglHabisKontrak.setText(Helper.parseToDateString(UItemKaryawan.getLogKontrak(), Consts.TYPE_DATE));
                }

                txtGajiEmp.setText("Rp " + Helper.numberFormat(Integer.valueOf(UItemKaryawan.getLogSalary())));
                // Toast the name to display temporarily on screen
                if (UItemKaryawan != null) {
                    Log.d("Data", String.valueOf(UItemKaryawan));
                }
            }
        }
    }

}

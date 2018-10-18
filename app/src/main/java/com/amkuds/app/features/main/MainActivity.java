package com.amkuds.app.features.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amkuds.app.AmkUdsApp;
import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.features.auth.LoginActivity;
import com.amkuds.app.features.auth.LogoutPresenter;
import com.amkuds.app.features.auth.LogoutPresenterImpl;
import com.amkuds.app.features.form_data.FormInputDataActivity;
import com.amkuds.app.features.list_data.ListDataEmployeeActivity;
import com.amkuds.app.utils.CallbackInterface;
import com.amkuds.app.utils.Consts;
import com.amkuds.app.utils.Helper;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    /*, LogoutPresenter.View*/

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindString(R.string.back_pressed)
    String strBackPressed;
    @BindString(R.string.msg_not_connect)
    String strInfoNotConnect;

    private long backPressedTime = 0;
    private Fragment mFragment = null;
    private FragmentManager fm;
    private ProgressDialog progressDialog;
    private LogoutPresenter mPresenter;
    private String role = "";
    private SharedPreferences sPref = AmkUdsApp.getInstance().Prefs();

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
//        mPresenter = new LogoutPresenterImpl(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("UD Sregep");
        setupActionDrawer();
        setupNavigationView();
        navigationView.setNavigationItemSelectedListener(this);
        gotoHome();

    }

    private void gotoHome() {
        role = sPref.getString(Consts.ROLE,"");
        if (role.equalsIgnoreCase("owner")){
            gotoFragment(fm, OwnerMainFragment.newInstance());
        } else{
            gotoFragment(fm, MainFragment.newInstance());
        }
    }

    private void setupNavigationView() {
        View header = navigationView.getHeaderView(0);
        TextView txtName = (TextView)header.findViewById(R.id.txtName);

        txtName.setText(sPref.getString(Consts.FIRSTNAME, ""));

        Menu navMenu = navigationView.getMenu();
        if (role.equalsIgnoreCase("owner")){
            navMenu.setGroupVisible(R.id.nav_menu_owner,true);
            navMenu.setGroupVisible(R.id.nav_menu_admin, false);
        }else {
            navMenu.setGroupVisible(R.id.nav_menu_owner,false);
            navMenu.setGroupVisible(R.id.nav_menu_admin, true);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                super.onBackPressed();
            } else {
                long t = System.currentTimeMillis();
                if (t - backPressedTime > 2000) {
                    backPressedTime = t;
                    Helper.createToast(this, strBackPressed);
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    private void setupActionDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setupNavigationView();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.nav_data_karyawan:
                intent = new Intent(this, ListDataEmployeeActivity.class);
                break;
            case R.id.nav_data_karyawann:
                intent = new Intent(this, ListDataEmployeeActivity.class);
                break;
            case R.id.nav_input_data_karyawan:
                intent = new Intent(this, FormInputDataActivity.class);
                break;
            case R.id.nav_logout:
                AmkUdsApp.getInstance().logout();
                AmkUdsApp.getInstance().getLogout();
                AmkUdsApp.getInstance().getRequest().cancel();
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK
                );
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
        if (mFragment != null){
            gotoFragment(fm, mFragment);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayData() {

    }

    /*@Override
    public void showLogout() {

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
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

    @Override
    public void notConnect(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, strInfoNotConnect);
    }*/
}

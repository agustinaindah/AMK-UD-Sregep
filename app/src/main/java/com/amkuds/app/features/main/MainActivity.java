package com.amkuds.app.features.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.features.input_data.FormInputDataActivity;
import com.amkuds.app.features.list_data.ListDataEmployeeActivity;
import com.amkuds.app.utils.Helper;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindString(R.string.back_pressed)
    String strBackPressed;

    private long backPressedTime = 0;
    private Fragment mFragment = null;
    private FragmentManager fm;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("UD Sregep");
        setupActionDrawer();
        setupNavigationView();
        navigationView.setNavigationItemSelectedListener(this);
        gotoHome();
    }

    private void gotoHome() {
        gotoFragment(fm, MainFragment.newInstance());
    }

    private void setupNavigationView() {
        View header = navigationView.getHeaderView(0);
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
            case R.id.nav_input_data_karyawan:
                intent = new Intent(this, FormInputDataActivity.class);
                break;
            case R.id.nav_logout:
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

}

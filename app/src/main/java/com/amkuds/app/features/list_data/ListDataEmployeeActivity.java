package com.amkuds.app.features.list_data;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.model.ItemKaryawan;
import com.amkuds.app.utils.CallbackInterface;
import com.amkuds.app.utils.Consts;
import com.amkuds.app.utils.EndlessScrollListener;
import com.amkuds.app.utils.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ListDataEmployeeActivity extends BaseActivity implements
        ListDataEmployeePresenter.View {

    @BindView(R.id.rvListEmployee)
    RecyclerView rvListEmployee;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.imgBtnFilter)
    ImageButton imgBtnFilter;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;

    private ListDataEmployeeAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int lastCount = 0;
    private ListDataEmployeePresenter mPresenter;
    private String mSearch;
    private String[] listItems;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List Data Karyawan");

        mPresenter = new ListDataEmployeePresenterImpl(this);
        mPresenter.getListEmployee(Consts.FIRST_PAGE);

        listItems = getResources().getStringArray(R.array.data_key_search);

        linearLayoutManager = new LinearLayoutManager(this);
        rvListEmployee.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int nextPage) {
                if (lastCount == Consts.LIMIT) {
                    mPresenter.getListEmployee(Consts.FIRST_PAGE);
                    mPresenter.getSearch(getQueryRequest(Consts.FIRST_PAGE));
                }
            }
        });
    }

    @Override
    protected int setView() {
        return R.layout.activity_list_karyawan;
    }

    @OnClick(R.id.imgBtnFilter)
    public void filterBy(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ListDataEmployeeActivity.this);
        mBuilder.setTitle("Filter By :");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mSearch = listItems[i].toLowerCase().replace(" ","_");
                dialogInterface.dismiss();
                showToast("Pencarian berdasarkan : " + Helper.capitalize(mSearch));
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    @OnClick(R.id.btnSearch)
    public void search(View view){
        if (mSearch == null || edtSearch.getText() == null) {
            Toast.makeText(this, "Pilih dan isi untuk melakukan pencarian", Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.getSearch(getQueryRequest(Consts.FIRST_PAGE));
        }
    }

    private Map<String, String> getQueryRequest(int page) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("key", mSearch);
        requestMap.put("value", edtSearch.getText().toString());
        requestMap.put("pagenum", String.valueOf(page));
        return requestMap;
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successListEmployee(List<ItemKaryawan> itemKaryawans, int page) {
        lastCount = itemKaryawans.size();

        if (page == Consts.FIRST_PAGE){
            txtNoData.setVisibility((itemKaryawans.size()==0)? View.VISIBLE : View.GONE);

            mAdapter = new ListDataEmployeeAdapter(itemKaryawans, this);

            rvListEmployee.setHasFixedSize(true);
            rvListEmployee.setLayoutManager(linearLayoutManager);
            rvListEmployee.setAdapter(mAdapter);
            rvListEmployee.setNestedScrollingEnabled(false);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);
            rvListEmployee.startAnimation(animation);
        } else {
            for (ItemKaryawan item : itemKaryawans) {
                mAdapter.add(item);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Helper.createAlert(this, "Info", msg, true, new CallbackInterface() {
            @Override
            public void callback() {
                mPresenter.getListEmployee(Consts.FIRST_PAGE);
                mPresenter.getSearch(getQueryRequest(Consts.FIRST_PAGE));
            }
        });
    }

    @Override
    public void notConnect(String msg) {
      layError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnError)
    public void reload(){
        layError.setVisibility(View.GONE);
        mPresenter.getListEmployee(Consts.FIRST_PAGE);
        mPresenter.getSearch(getQueryRequest(Consts.FIRST_PAGE));
    }
}

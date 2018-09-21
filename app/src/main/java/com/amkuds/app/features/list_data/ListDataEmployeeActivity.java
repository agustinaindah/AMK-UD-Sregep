package com.amkuds.app.features.list_data;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseActivity;
import com.amkuds.app.model.Employee;
import com.amkuds.app.utils.Consts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListDataEmployeeActivity extends BaseActivity {

    @BindView(R.id.rvListEmployee)
    RecyclerView rvListEmployee;
    @BindView(R.id.cardFilter)
    CardView cardFilter;

    private List<Employee> employeeList = new ArrayList<>();
    private ListDataEmployeeAdapter listDataEmployeeAdapter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Karyawan");

        listDataEmployeeAdapter = new ListDataEmployeeAdapter(this, employeeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListEmployee.setLayoutManager(linearLayoutManager);
        rvListEmployee.setAdapter(listDataEmployeeAdapter);

        employeeDataList();
    }

    private void employeeDataList() {
        String url = "https://vignette.wikia.nocookie.net/spongebob/images/a/ac/Spongebobwithglasses.jpeg/revision/latest?cb=20121014113150";
        Employee empOne = new Employee("Tio Tamara", url, "Back End Programmer", "Kontrak","17 Sept 2019");
        Employee empTwo = new Employee("Tio Tamara", url, "Back End Programmer", "Kontrak","17 Sept 2019");
        Employee empThree = new Employee("Tio Tamara", url, "Back End Programmer", "Kontrak","17 Sept 2019");
        Employee empFour = new Employee("Tio Tamara", url, "Back End Programmer", "Kontrak", "17 Sept 2019");
        Employee empFive = new Employee("Tio Tamara", url, "Back End Programmer", "Kontrak","17 Sept 2019");
        Employee empSix = new Employee("Tio Tamara", url, "Back End Programmer", "Kontrak","17 Sept 2019");

        employeeList.add(empOne);
        employeeList.add(empTwo);
        employeeList.add(empThree);
        employeeList.add(empFour);
        employeeList.add(empFive);
        employeeList.add(empSix);
        listDataEmployeeAdapter.notifyDataSetChanged();
    }

    @Override
    protected int setView() {
        return R.layout.activity_list_karyawan;
    }

    @OnClick(R.id.cardFilter)
    public void filterBy(){
        DialogFragment dialogFragment = DialogFilterKaryawan.newInstance();
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }
}

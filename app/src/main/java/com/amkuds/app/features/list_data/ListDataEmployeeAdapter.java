package com.amkuds.app.features.list_data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amkuds.app.R;
import com.amkuds.app.model.Employee;
import com.amkuds.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListDataEmployeeAdapter extends RecyclerView.Adapter<ListDataEmployeeAdapter.ViewHolder> {

    /*data dummy*/

    private Context context;
    private List<Employee> employees;

    public ListDataEmployeeAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @Override
    public ListDataEmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_karyawan,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListDataEmployeeAdapter.ViewHolder holder, int position) {
        final Employee employee = (Employee) employees.get(position);

        Helper.displayImage(context, employee.getEmployeeImage(), holder.imgItemEmployee);
        holder.txtNameEmp.setText(employee.getEmployeeName());
        holder.txtJabatanEmp.setText(employee.getEmployeePosition());
        holder.txtSttsEmp.setText(employee.getEmployeeStatus());
        holder.txtDateEndEmp.setText(employee.getEmployeeEndContract());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imgItemEmployee)
        ImageView imgItemEmployee;
        @BindView(R.id.txtNameEmp)
        TextView txtNameEmp;
        @BindView(R.id.txtJabatanEmp)
        TextView txtJabatanEmp;
        @BindView(R.id.txtSttsEmp)
        TextView txtSttsEmp;
        @BindView(R.id.txtDateEndEmp)
        TextView txtDateEndEmp;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

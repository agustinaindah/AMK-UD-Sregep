package com.amkuds.app.features.list_data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseRecyclerViewAdapter;
import com.amkuds.app.features.list_data.detail.ListDetailEmployeeActivity;
import com.amkuds.app.model.ItemKaryawan;
import com.amkuds.app.utils.Consts;
import com.amkuds.app.utils.Helper;
import com.amkuds.app.utils.ViewHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDataEmployeeAdapter extends BaseRecyclerViewAdapter<ItemKaryawan> {

    private final int REQUEST_CODE = 30;

    public ListDataEmployeeAdapter(List<ItemKaryawan> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ViewHelper.inflateView(parent, R.layout.item_list_karyawan);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position),(ViewHolder) holder);
    }

    private void renderData(final ItemKaryawan itemKaryawan, ViewHolder holder) {
        Helper.displayImage(mContext, itemKaryawan.getFoto(), holder.imgItemEmployee, true);
        holder.txtNameEmp.setText(Helper.capitalize(itemKaryawan.getNama()));
        holder.txtJabatanEmp.setText(Helper.capitalize(itemKaryawan.getJabatan()));
        holder.txtSttsEmp.setText(Helper.capitalize(itemKaryawan.getStatusKaryawan()));
        if (itemKaryawan.getLogKontrak() == null){
            holder.txtDateEndContractEmp.setText("Kosong");
        } else {
            holder.txtDateEndContractEmp.setText(Helper.parseToDateString(itemKaryawan.getLogKontrak(), Consts.TYPE_DATE));
        }

        holder.layItemEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListDetailEmployeeActivity.class);
                intent.putExtra(Consts.ARG_ID, itemKaryawan.getId());
                intent.putExtra(Consts.ARG_DATA, itemKaryawan);
                ((Activity) mContext).startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.layItemEmployee)
        RelativeLayout layItemEmployee;
        @BindView(R.id.imgItemEmployee)
        ImageView imgItemEmployee;
        @BindView(R.id.txtNameEmp)
        TextView txtNameEmp;
        @BindView(R.id.txtJabatanEmp)
        TextView txtJabatanEmp;
        @BindView(R.id.txtSttsEmp)
        TextView txtSttsEmp;
        @BindView(R.id.txtDateEndContractEmp)
        TextView txtDateEndContractEmp;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

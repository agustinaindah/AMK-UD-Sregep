package com.amkuds.app.features.list_data;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.amkuds.app.R;
import com.amkuds.app.base.BaseDialogFragment;

public class DialogFilterKaryawan extends BaseDialogFragment{

    public static DialogFilterKaryawan newInstance() {

        Bundle args = new Bundle();

        DialogFilterKaryawan fragment = new DialogFilterKaryawan();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected AlertDialog setupDialog(AlertDialog.Builder builder) {
        builder
                .setTitle("Filter by :")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DialogFilterKaryawan.this.getDialog().cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    @Override
    protected int setView() {
        return R.layout.dialog_fragment_filter;
    }
}

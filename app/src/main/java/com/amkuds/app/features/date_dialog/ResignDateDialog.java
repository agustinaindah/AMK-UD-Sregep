package com.amkuds.app.features.date_dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.amkuds.app.utils.Consts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ResignDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "ResignDateDialog";

    private static ResignOnDateDialog mCallback;

    public static ResignDateDialog newInstance(String date, ResignOnDateDialog listener) {
        Bundle args = new Bundle();
        args.putString(Consts.ARG_DATA, date);
        ResignDateDialog fragment = new ResignDateDialog();
        mCallback = listener;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance(new Locale("id"));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Consts.TYPE_DATE, new Locale("id"));
            c.setTime(sdf.parse(getArguments().getString(Consts.ARG_DATA)));
        } catch (ParseException e) {

        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day );
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        StringBuilder sbDate = new StringBuilder("");
        sbDate.append(year)
                .append("-")
                .append((monthOfYear + 1))
                .append("-")
                .append(dayOfMonth);
        mCallback.onResignSelectedDate(sbDate + "");
    }

    public interface ResignOnDateDialog {
        void onResignSelectedDate(String resignDate);
    }
}
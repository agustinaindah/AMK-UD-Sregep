package com.amkuds.app;

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

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "DateDialog";

    private static OnDateDialog mCallback;

    public static DateDialog newInstance(String date, OnDateDialog listener) {
        Bundle args = new Bundle();
        args.putString(Consts.ARG_DATA, date);
        DateDialog fragment = new DateDialog();
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

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        StringBuilder sbDate = new StringBuilder("");
        sbDate.append(year)
                .append("-")
                .append((monthOfYear + 1))
                .append("-")
                .append(dayOfMonth);
        mCallback.onSelectedDate(sbDate + "");
    }

    public interface OnDateDialog {
        void onSelectedDate(String selectedDate);
    }
}

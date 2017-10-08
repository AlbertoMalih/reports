package com.example.reports.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.reports.MainActivity;
import com.example.reports.R;
import com.example.reports.RegisterUserActivity;
import com.example.reports.RegisterWarderActivity;
import com.example.reports.ShowReportActivity;
import com.example.reports.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 26.09.2017.
 */

public class Dialogs {
    public static final int START_WARDER_REGISTRATION_ACTIVITY = 0;
    public static final int START_USER_REGISTRATION_ACTIVITY = 1;

    public static AlertDialog createChooseRegistration(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder
                .setTitle("choose registration or login")
                .setItems(
                        activity.getResources().getStringArray(R.array.activities_for_registration),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                switch (item) {
                                    case START_WARDER_REGISTRATION_ACTIVITY:
                                        activity.startActivityForResult(new Intent(activity, RegisterWarderActivity.class), MainActivity.WARDER_REGISTRATION_ACTIVITY_REQUEST);
                                        break;
                                    case START_USER_REGISTRATION_ACTIVITY:
                                        activity.startActivityForResult(new Intent(activity, RegisterUserActivity.class), MainActivity.USER_REGISTRATION_ACTIVITY_REQUEST);
                                        break;
                                }
                            }
                        });
        return builder.create();
    }

    public static AlertDialog createChooseNestedMonthsInYear(final Activity activity, final Integer[] months,
                                                             final Map<Integer, Map<String, User>> usersDataFromYear) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder
                .setTitle("choose month in year")
                .setItems(
                        Utils.convertIntegerMonthsToString(activity, months),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                HashMap<Integer, Map<String, User>> result = new HashMap<>();
                                result.put(item, usersDataFromYear.get(months[item]));
                                activity.startActivity(
                                        new Intent(activity, ShowReportActivity.class).
                                                putExtra(ShowReportActivity.MONTHS_DATA, result)
                                                .putExtra(ShowReportActivity.ATTRIBUTE_SHOW_IN_YEAR, false)
                                );
                            }
                        });
        return builder.create();
    }

    public static DatePickerDialog createChooseDateForShowReport(final Activity activity, final FirebaseDatabase database, final Map<String, Integer> propertiesForWrite,
                                                                 final String yourNameInDb, final String warderId) {
        Calendar defaultDate = Calendar.getInstance();
        defaultDate.set(Calendar.MONTH, defaultDate.get(Calendar.MONTH) - 1);
        DatePickerDialog result = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        database.getReference(
                                new StringBuilder("sobranies/")
                                        .append(warderId).append("/")
                                        .append(year + "").append("/")
                                        .append(monthOfYear + "").append("/")
                                        .append(yourNameInDb)
                                        .toString())
                                .setValue(propertiesForWrite);
                    }
                }
                , defaultDate.get(Calendar.YEAR), defaultDate.get(Calendar.MONTH), defaultDate.get(Calendar.DAY_OF_MONTH));

        return result;
    }
}
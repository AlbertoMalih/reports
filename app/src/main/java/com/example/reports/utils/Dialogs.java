package com.example.reports.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.reports.MainActivity;
import com.example.reports.R;
import com.example.reports.RegisterUserActivity;
import com.example.reports.RegisterWarderActivity;
import com.example.reports.ShowReportActivity;

import java.util.HashMap;
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

    public static AlertDialog createChooseNestedMonthsInYear(final Activity activity, final Integer[] months, final Map<Integer, Map<String, Map<String, Long>>> usersDataFromYear) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder
                .setTitle("choose month in year")
                .setItems(
                        Utils.convertIntegerMonthsToString(activity, months),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                activity.startActivity(
                                        new Intent(activity, ShowReportActivity.class).putExtra(ShowReportActivity.MONTHS_DATA, ((HashMap) usersDataFromYear.get(months[item])))
                                );
                            }
                        });
        return builder.create();
    }

}
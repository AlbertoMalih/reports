package com.example.reports;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.reports.model.User;
import com.example.reports.utils.Dialogs;
import com.example.reports.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;

public class ChooseDataReportActivity extends BaseActivity {
    @Inject
    FirebaseDatabase firebaseDatabase;
    @Inject
    FirebaseAuth firebaseAuth;
    private Map<Integer, Map<Integer, Map<String, User>>> allDataFromDb;

    @BindView(R.id.lv_show_all_years_from_container_in_db)
    ListView inputYearsAndNestedMonths;
    private boolean inputShowInYear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
        }
        initializeDbData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_data_report;
    }

    @Override
    public void injectDependencies() {
        getActivityComponent().inject(this);
    }

    private void initializeDbData() {
        (firebaseDatabase.getReference("sobranies")
                .child(getActivityComponent().firebaseAuth().getCurrentUser().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        allDataFromDb = new HashMap<>();
                        Log.d(MainActivity.TAG, dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Map<String, Map<String, User>>>>() {
                        }).toString());
                        for (Map.Entry<String, Map<String, Map<String, User>>> yearsInData :
                                dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Map<String, Map<String, User>>>>() {
                                }).entrySet()) {

                            Map<Integer, Map<String, User>> monthsFromYear = new HashMap<>();
                            for (Map.Entry<String, Map<String, User>> month : yearsInData.getValue().entrySet()) {
                                monthsFromYear.put(Integer.parseInt(month.getKey()), month.getValue());
                            }
                            allDataFromDb.put(Integer.parseInt(yearsInData.getKey()), monthsFromYear);
                        }
                        inputYearsAndNestedMonths.setAdapter(
                                new ArrayAdapter<>(ChooseDataReportActivity.this, android.R.layout.simple_spinner_item, Utils.convertIntegerToCharSequense(allDataFromDb.keySet()))
                        );
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    @OnItemClick(R.id.lv_show_all_years_from_container_in_db)
    public void onClickYearsAndNestedMonths(int position) {
        int year = allDataFromDb.keySet().toArray(new Integer[0])[position];
        if (inputShowInYear) {
            startActivity(
                    new Intent(this, ShowReportActivity.class).
                            putExtra(ShowReportActivity.MONTHS_DATA, ((HashMap) allDataFromDb.get(year)))
                            .putExtra(ShowReportActivity.ATTRIBUTE_SHOW_IN_YEAR, true)
            );
        } else {
            Dialogs.createChooseNestedMonthsInYear(
                    ChooseDataReportActivity.this, allDataFromDb.get(year).keySet().toArray(new Integer[0]),
                    allDataFromDb.get(year)
            ).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_choose_data, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_in_year:
                if (item.isChecked()) {
                    item.setChecked(false);
                }
                else item.setChecked(true);

                inputShowInYear = item.isChecked();

//                item.setChecked(item.isChecked());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
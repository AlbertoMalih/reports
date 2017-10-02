package com.example.reports;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.reports.utils.Dialogs;
import com.example.reports.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ChooseDataReportActivity extends BaseActivity {
    @Inject
    FirebaseDatabase firebaseDatabase;
    @Inject
    FirebaseAuth firebaseAuth;
    private Map<Integer, List<Integer>> yearsAndMonths;
    private Map<Integer, Map<Integer, Map<String, Map<String, Long>>>> allDataFromDb;
    private ListView yearsAndNestedMonths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_data_report);
        yearsAndNestedMonths = ((ListView) findViewById(R.id.lv_show_all_years_from_container_in_db));

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
        }
        initializeFromDb();
    }

    @Override
    public void injectDependencies() {
        getActivityComponent().inject(this);
    }

    private void initializeFromDb() {
        (firebaseDatabase.getReference("sobranies")
                .child(getActivityComponent().firebaseAuth().getCurrentUser().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(MainActivity.TAG, " in initializeFromDb");
                        Map<String, Map<String, Map<String, Map<String, Long>>>> tempMap = (Map<String, Map<String, Map<String, Map<String, Long>>>>) dataSnapshot.getValue();
                        yearsAndMonths = new HashMap<>();
                        allDataFromDb = new HashMap<>();

                        for (Map.Entry<String, Map<String, Map<String, Map<String, Long>>>> yearsInData : tempMap.entrySet()) {

                            int year = Integer.parseInt(yearsInData.getKey());
                            List<Integer> months = new ArrayList<>();
                            Map<Integer, Map<String, Map<String, Long>>> monthForAllData = new HashMap<>();

                            for (Map.Entry<String, Map<String, Map<String, Long>>> month : yearsInData.getValue().entrySet()) {
                                int monthInteger = Integer.parseInt(month.getKey());
                                months.add(monthInteger);
                                monthForAllData.put(monthInteger, month.getValue());
                            }
                            yearsAndMonths.put(year, months);
                            allDataFromDb.put(year, monthForAllData);
                        }
                        yearsAndNestedMonths.setAdapter(
                                new ArrayAdapter<>(
                                        ChooseDataReportActivity.this, android.R.layout.simple_spinner_item, Utils.convertIntegerToCharSequense(yearsAndMonths.keySet())
                                )
                        );
                        yearsAndNestedMonths.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int year = yearsAndMonths.keySet().toArray(new Integer[0])[position];
                                Dialogs.createChooseNestedMonthsInYear(
                                        ChooseDataReportActivity.this, allDataFromDb.get(year).keySet().toArray(new Integer[0]),
                                        allDataFromDb.get(year)
                                ).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}

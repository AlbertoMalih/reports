package com.example.reports;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.reports.utils.Dialogs;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    public static final String ID_GROUP_WARDER_IN_PREFERENCE = "ID_GROUP_WARDER";
    public static final String TAG = "MainActivityTag";
    public static final String YOUR_NAME_IN_PREFERENCE = "YOUR_NAME";
    public static final int WARDER_REGISTRATION_ACTIVITY_REQUEST = 51;
    public static final int USER_REGISTRATION_ACTIVITY_REQUEST = 52;
    public static final String KEY_FOR_DB_SIZE_PUBLICATIONS = "sizePublications";
    public static final String KEY_FOR_DB_SIZE_VIDEOS = "sizeVideos";
    public static final String KEY_FOR_DB_SIZE_HOURS = "sizeHours";
    public static final String KEY_FOR_DB_SIZE_REPEAT_VISITS = "sizeRepeatVisits";
    public static final String KEY_FOR_DB_SIZE_STUDYING_BIBLE = "sizeStudyingBible";

    @BindView(R.id.et_size_publication)
    EditText inputTextSizePublications;
    @BindView(R.id.et_size_hours)
    EditText inputTextSizeHours;
    @BindView(R.id.et_size_repeated_visits)
    EditText inputTextSizeRepeatVisits;
    @BindView(R.id.et_size_studying_bible)
    EditText inputTextSizeStudyingBible;
    @BindView(R.id.et_size_videos)
    EditText inputTextSizeVideos;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        if (getActivityComponent().firebaseAuth().getCurrentUser() == null && sharedPreferences.getString(ID_GROUP_WARDER_IN_PREFERENCE, "").isEmpty()) {
            chooseAndCarryOutAuthorization(null);
        }
    }

    @Override
    public void injectDependencies() {
        getActivityComponent().inject(this);
    }

    private void chooseAndCarryOutAuthorization(View v) {
        Dialogs.createChooseRegistration(this).show();
    }

    public void sendReport(View v) {
        String warderId = sharedPreferences.getString(ID_GROUP_WARDER_IN_PREFERENCE, "");
        if (warderId.isEmpty()) {
            chooseAndCarryOutAuthorization(null);
        }

        int sizePublications = 0;
        try {
            sizePublications = Integer.parseInt(inputTextSizePublications.getText().toString());
        } catch (NumberFormatException e) {
        }
        int sizeVideos = 0;
        try {
            sizeVideos = Integer.parseInt(inputTextSizeVideos.getText().toString());
        } catch (NumberFormatException e) {
        }
        int sizeHours = 0;
        try {
            sizeHours = Integer.parseInt(inputTextSizeHours.getText().toString());
        } catch (NumberFormatException e) {
        }
        int sizeRepeatVisits = 0;
        try {
            sizeRepeatVisits = Integer.parseInt(inputTextSizeRepeatVisits.getText().toString());
        } catch (NumberFormatException e) {
        }
        int sizeStudyingBible = 0;
        try {
            sizeStudyingBible = Integer.parseInt(inputTextSizeStudyingBible.getText().toString());
        } catch (NumberFormatException e) {
        }

        Map<String, Integer> valuesForInsertToDB = new HashMap<>();
        valuesForInsertToDB.put(KEY_FOR_DB_SIZE_PUBLICATIONS, sizePublications);
        valuesForInsertToDB.put(KEY_FOR_DB_SIZE_VIDEOS, sizeVideos);
        valuesForInsertToDB.put(KEY_FOR_DB_SIZE_HOURS, sizeHours);
        valuesForInsertToDB.put(KEY_FOR_DB_SIZE_REPEAT_VISITS, sizeRepeatVisits);
        valuesForInsertToDB.put(KEY_FOR_DB_SIZE_STUDYING_BIBLE, sizeStudyingBible);
        Dialogs.createChooseDateForShowReport(this, getActivityComponent().firebaseDatabase(), valuesForInsertToDB, sharedPreferences.getString(YOUR_NAME_IN_PREFERENCE, ""), warderId).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_registration_item:
                chooseAndCarryOutAuthorization(null);
                break;
            case R.id.main_menu_show_report_item:
                startActivity(new Intent(this, ChooseDataReportActivity.class));
                break;
            case R.id.main_menu_show_about_application:
                startActivity(new Intent(this, AboutAppActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected int getLayoutId(){
        return R.layout.activity_main;
    }
}
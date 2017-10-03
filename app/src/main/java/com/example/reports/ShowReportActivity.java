package com.example.reports;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowReportActivity extends AppCompatActivity {
    public static final String MONTHS_DATA = "MONTHS_DATA";
    private Map<String, Map<String, Long>> users;
    private ListView listViewAllUsersWhoSendReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);
        users = (Map<String, Map<String, Long>>) getIntent().getSerializableExtra(MONTHS_DATA);
        listViewAllUsersWhoSendReport = (ListView) findViewById(R.id.all_users_who_send_report);
        initialize();
    }

    private void initialize() {
        int sizePublications = 0;
        int sizeVideos = 0;
        int sizeHours = 0;
        int sizeRepeatVisits = 0;
        int sizeStudyingBible = 0;
        List<String> names = new ArrayList<>();
        for (Map.Entry<String, Map<String, Long>> nameAndUserEntry : users.entrySet()) {
            if (nameAndUserEntry.getKey().equals("group_warder")) {
                continue;
            }
            names.add(nameAndUserEntry.getKey());
            sizePublications += nameAndUserEntry.getValue().get(MainActivity.KEY_FOR_DB_IZE_PUBLICATIONS);
            sizeVideos += nameAndUserEntry.getValue().get(MainActivity.KEY_FOR_DB_SIZE_VIDEOS);
            sizeHours += nameAndUserEntry.getValue().get(MainActivity.KEY_FOR_DB_SIZE_HOURS);
            sizeRepeatVisits += nameAndUserEntry.getValue().get(MainActivity.KEY_FOR_DB_SIZE_REPEAT_VISITS);
            sizeStudyingBible += nameAndUserEntry.getValue().get(MainActivity.KEY_FOR_DB_SIZE_STUDYING_BIBLE);
        }
        ((TextView) findViewById(R.id.et_size_publication_show_report)).append(":  " + String.valueOf(sizePublications));
        ((TextView) findViewById(R.id.et_size_videos_show_report)).append(":  " + String.valueOf(sizeVideos));
        ((TextView) findViewById(R.id.et_size_hours_show_report)).append(":  " + String.valueOf(sizeHours));
        ((TextView) findViewById(R.id.et_size_repeated_visits_show_report)).append(":  " + String.valueOf(sizeRepeatVisits));
        ((TextView) findViewById(R.id.et_size_studying_bible_show_report)).append(":  " + String.valueOf(sizeStudyingBible));
        listViewAllUsersWhoSendReport.setAdapter(new ArrayAdapter<>(this, R.layout.item_simple_name, R.id.TextView, names.toArray(new String[names.size()])));
    }
}
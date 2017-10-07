package com.example.reports;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.example.reports.MainActivity.KEY_FOR_DB_SIZE_PUBLICATIONS;
import static com.example.reports.MainActivity.KEY_FOR_DB_SIZE_HOURS;
import static com.example.reports.MainActivity.KEY_FOR_DB_SIZE_REPEAT_VISITS;
import static com.example.reports.MainActivity.KEY_FOR_DB_SIZE_STUDYING_BIBLE;
import static com.example.reports.MainActivity.KEY_FOR_DB_SIZE_VIDEOS;

public class ShowReportActivity extends BaseActivity {
    public static final String MONTHS_DATA = "MONTHS_DATA";
    final String ATTRIBUTE_NAME = "ATTRIBUTE_NAME";

    private Map<String, Map<String, Long>> users;
//    @BindView(R.id.all_users_who_send_report)
//    ListView allUsersWhoSendReport;
    @BindView(R.id.all_users_who_send_report_and_data)
    ListView allUsersWhoSendReportAndData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = (Map<String, Map<String, Long>>) getIntent().getSerializableExtra(MONTHS_DATA);
        initialize();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_report;
    }

    @Override
    public void injectDependencies() {

    }

    private void initialize() {
        int sizePublications = 0;
        int sizeVideos = 0;
        int sizeHours = 0;
        int sizeRepeatVisits = 0;
        int sizeStudyingBible = 0;
        List<String> names = new ArrayList<>();
        List<Map<String, String>> itemsNamesAndData = new ArrayList<>();
        Map<String, String> itemData;
        for (Map.Entry<String, Map<String, Long>> nameAndUserEntry : users.entrySet()) {
            if (nameAndUserEntry.getKey().equals("group_warder")) {
                continue;
            }
            names.add(nameAndUserEntry.getKey());
            sizePublications += nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_PUBLICATIONS);
            sizeVideos += nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_VIDEOS);
            sizeHours += nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_HOURS);
            sizeRepeatVisits += nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_REPEAT_VISITS);
            sizeStudyingBible += nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_STUDYING_BIBLE);

            itemData = new HashMap<>();
            itemData.put(ATTRIBUTE_NAME, nameAndUserEntry.getKey());
            itemData.put(KEY_FOR_DB_SIZE_PUBLICATIONS, getString(R.string.size_publications).concat(String.valueOf(nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_PUBLICATIONS))));
            itemData.put(KEY_FOR_DB_SIZE_HOURS, getString(R.string.size_hours).concat(String.valueOf( nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_HOURS))));
            itemData.put(KEY_FOR_DB_SIZE_VIDEOS, getString(R.string.size_videos).concat(String.valueOf(nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_VIDEOS))));
            itemData.put(KEY_FOR_DB_SIZE_REPEAT_VISITS, getString(R.string.size_repeated_visits).concat(String.valueOf( nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_REPEAT_VISITS))));
            itemData.put(KEY_FOR_DB_SIZE_STUDYING_BIBLE, getString(R.string.size_studying_bible).concat(String.valueOf( nameAndUserEntry.getValue().get(KEY_FOR_DB_SIZE_STUDYING_BIBLE))));
            itemsNamesAndData.add(itemData);
        }
        ((TextView) findViewById(R.id.size_publication_show_report_of_user)).append(String.valueOf(sizePublications));
        ((TextView) findViewById(R.id.size_videos_show_report_of_user)).append(String.valueOf(sizeVideos));
        ((TextView) findViewById(R.id.size_hours_show_report_of_user)).append(String.valueOf(sizeHours));
        ((TextView) findViewById(R.id.size_repeated_visits_show_report_of_user)).append(String.valueOf(sizeRepeatVisits));
        ((TextView) findViewById(R.id.size_studying_bible_show_report_of_user)).append(String.valueOf(sizeStudyingBible));
//        allUsersWhoSendReport.setAdapter(new ArrayAdapter<>(this, R.layout.item_simple_name, R.id.TextView, names.toArray(new String[names.size()])));

        allUsersWhoSendReportAndData.setAdapter(new SimpleAdapter(
                this, itemsNamesAndData, R.layout.item_name_and_data_user, new String[]{
                ATTRIBUTE_NAME, KEY_FOR_DB_SIZE_PUBLICATIONS, KEY_FOR_DB_SIZE_HOURS, KEY_FOR_DB_SIZE_VIDEOS, KEY_FOR_DB_SIZE_REPEAT_VISITS, KEY_FOR_DB_SIZE_STUDYING_BIBLE
        }, new int[]{
                R.id.name_of_user, R.id.size_publication_show_report_of_user, R.id.size_hours_show_report_of_user, R.id.size_videos_show_report_of_user,
                R.id.size_repeated_visits_show_report_of_user, R.id.size_studying_bible_show_report_of_user
        }
        ));
    }
}
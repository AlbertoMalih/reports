package com.example.reports;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.reports.model.User;

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
    public static final String ATTRIBUTE_SHOW_IN_YEAR = "ATTRIBUTE_SHOW_IN_YEAR";
    final String ATTRIBUTE_NAME = "ATTRIBUTE_NAME";

    private Map<Integer, Map<String, User>> dataOfUsers;
    @BindView(R.id.all_users_who_send_report_and_data)
    ListView allUsersWhoSendReportAndData;
    @BindView(R.id.size_publications_of_user_show_report)
    TextView outputSizePublicationsOfUserShowReport;
    @BindView(R.id.size_videos_of_user_show_report)
    TextView outputSizeVideosOfUserShowReport;
    @BindView(R.id.size_hours_show_report_of_user)
    TextView outputSizeHoursOfUserShowReport;
    @BindView(R.id.size_repeated_visits_show_report_of_user)
    TextView outputSizeRepeatVisitsOfUserShowReport;
    @BindView(R.id.size_studying_bible_show_report_of_user)
    TextView outputSizeStudyingBibleOfUserShowReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dataOfUsers = (Map) getIntent().getSerializableExtra(MONTHS_DATA);
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
        Map<String, User> dataToShow = new HashMap<>();
        for (Map.Entry<Integer, Map<String, User>> monthsEntry : dataOfUsers.entrySet()) {

            for (Map.Entry<String, User> nameInMonthEntry : monthsEntry.getValue().entrySet()) {
                if (nameInMonthEntry.getKey().equals("group_warder")) {
                    continue;
                }
                User currentUser = nameInMonthEntry.getValue();
                sizePublications += currentUser.getSizePublications();
                sizeVideos += currentUser.getSizeVideos();
                sizeHours += currentUser.getSizeHours();
                sizeRepeatVisits += currentUser.getSizeRepeatVisits();
                sizeStudyingBible += currentUser.getSizeStudyingBible();

                User userToShow = dataToShow.get(nameInMonthEntry.getKey());
                if (userToShow == null) {
                    dataToShow.put(nameInMonthEntry.getKey(), currentUser);
                } else {
                    userToShow.addHours(currentUser.getSizeHours());
                    userToShow.addPublications(currentUser.getSizePublications());
                    userToShow.addRepeatVisits(currentUser.getSizeRepeatVisits());
                    userToShow.addStudyingsBible(currentUser.getSizeStudyingBible());
                    userToShow.addVideos(currentUser.getSizeVideos());
                }
            }
        }

        List<Map<String, String>> itemsNamesAndData = new ArrayList<>();
        Map<String, String> itemData;
        for (Map.Entry<String, User> nameAndUserEntry : dataToShow.entrySet()) {
            itemData = new HashMap<>();
            User currentUser = nameAndUserEntry.getValue();
            itemData.put(ATTRIBUTE_NAME, nameAndUserEntry.getKey());
            itemData.put(KEY_FOR_DB_SIZE_PUBLICATIONS, getString(R.string.size_publications).concat(String.valueOf(currentUser.getSizePublications())));
            itemData.put(KEY_FOR_DB_SIZE_HOURS, getString(R.string.size_hours).concat(String.valueOf(currentUser.getSizeHours())));
            itemData.put(KEY_FOR_DB_SIZE_VIDEOS, getString(R.string.size_videos).concat(String.valueOf(currentUser.getSizeVideos())));
            itemData.put(KEY_FOR_DB_SIZE_REPEAT_VISITS, getString(R.string.size_repeated_visits).concat(String.valueOf(currentUser.getSizeRepeatVisits())));
            itemData.put(KEY_FOR_DB_SIZE_STUDYING_BIBLE, getString(R.string.size_studying_bible).concat(String.valueOf(currentUser.getSizeStudyingBible())));
            itemsNamesAndData.add(itemData);
        }

        outputSizePublicationsOfUserShowReport.append(String.valueOf(sizePublications));
        outputSizeVideosOfUserShowReport.append(String.valueOf(sizeVideos));
        outputSizeHoursOfUserShowReport.append(String.valueOf(sizeHours));
        outputSizeRepeatVisitsOfUserShowReport.append(String.valueOf(sizeRepeatVisits));
        outputSizeStudyingBibleOfUserShowReport.append(String.valueOf(sizeStudyingBible));

        allUsersWhoSendReportAndData.setAdapter(new SimpleAdapter(
                this, itemsNamesAndData, R.layout.item_name_and_data_user, new String[]{
                ATTRIBUTE_NAME, KEY_FOR_DB_SIZE_PUBLICATIONS, KEY_FOR_DB_SIZE_HOURS, KEY_FOR_DB_SIZE_VIDEOS, KEY_FOR_DB_SIZE_REPEAT_VISITS, KEY_FOR_DB_SIZE_STUDYING_BIBLE
        }, new int[]{
                R.id.name_of_user, R.id.size_publications_of_user_show_report, R.id.size_hours_show_report_of_user, R.id.size_videos_of_user_show_report,
                R.id.size_repeated_visits_show_report_of_user, R.id.size_studying_bible_show_report_of_user
        }
        ));
    }
}
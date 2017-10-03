package com.example.reports;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import static com.example.reports.MainActivity.ID_GROUP_WARDER_IN_PREFERENCE;
import static com.example.reports.MainActivity.YOUR_NAME_IN_PREFERENCE;

public class RegisterUserActivity extends BaseActivity {
    private EditText tv_id_warder_group;
    private EditText tv_your_name;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        tv_id_warder_group = (EditText) findViewById(R.id.tv_id_warder_group);
        tv_your_name = (EditText) findViewById(R.id.tv_your_name);
    }

    @Override
    public void injectDependencies() {
        getActivityComponent().inject(this);
    }

    public void registration(View v) {
        String login = tv_id_warder_group.getText().toString();
        String password = tv_your_name.getText().toString();
        if (password.isEmpty() || login.isEmpty()) {
            return;
        }
        sharedPreferences.edit()
                .putString(ID_GROUP_WARDER_IN_PREFERENCE, tv_id_warder_group.getText().toString().trim())
                .putString(YOUR_NAME_IN_PREFERENCE, tv_your_name.getText().toString().trim())
        .apply();

        setResult(Activity.RESULT_OK);

        finish();
    }
}

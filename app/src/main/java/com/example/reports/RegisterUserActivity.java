package com.example.reports;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;

import static com.example.reports.MainActivity.ID_GROUP_WARDER_IN_PREFERENCE;
import static com.example.reports.MainActivity.YOUR_NAME_IN_PREFERENCE;

public class RegisterUserActivity extends BaseActivity {
    @BindView(R.id.tv_id_warder_group)
     EditText inputTextIdWarderGroup;
    @BindView(R.id.tv_your_name)
    EditText inputTextYourName;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_user;
    }

    @Override
    public void injectDependencies() {
        getActivityComponent().inject(this);
    }

    public void registration(View v) {
        String login = inputTextIdWarderGroup.getText().toString();
        String password = inputTextYourName.getText().toString();
        if (password.isEmpty() || login.isEmpty()) {
            return;
        }
        sharedPreferences.edit()
                .putString(ID_GROUP_WARDER_IN_PREFERENCE, inputTextIdWarderGroup.getText().toString().trim())
                .putString(YOUR_NAME_IN_PREFERENCE, inputTextYourName.getText().toString().trim())
        .apply();

        setResult(Activity.RESULT_OK);

        finish();
    }
}

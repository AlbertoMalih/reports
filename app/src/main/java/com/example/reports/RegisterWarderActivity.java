package com.example.reports;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

public class RegisterWarderActivity extends BaseActivity {
    @BindView(R.id.login_warder)
    EditText inputTextLogin;
    @BindView(R.id.password_warder)
    EditText inputTextPassword;
    @BindView(R.id.generated_group_id)
    TextView outputViewShowGroupId;
    @Inject
    FirebaseAuth firebaseAuth;
    @Inject
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_warder;
    }

    @Override
    public void injectDependencies() {
        getActivityComponent().inject(this);
    }

    public void registration(View v) {
        String login = inputTextLogin.getText().toString();
        String password = inputTextPassword.getText().toString();
        if (password.isEmpty() || login.isEmpty()) {
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(onCompleteListenerRegister);
    }

    public void login(View v) {
        String login = inputTextLogin.getText().toString().trim();
        String password = inputTextPassword.getText().toString().trim();
        if (password.isEmpty() || login.isEmpty()) {
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(login, password).
                addOnCompleteListener(onCompleteListenerLogin);
    }

    private OnCompleteListener<AuthResult> onCompleteListenerRegister = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                Map<String, Map<String, Long>> dataUsersInSingleСollection = new HashMap<>();
                Map<String, Long> nullData = new HashMap<>();
                nullData.put(MainActivity.KEY_FOR_DB_SIZE_HOURS, 0L);
                nullData.put(MainActivity.KEY_FOR_DB_SIZE_PUBLICATIONS, 0L);
                nullData.put(MainActivity.KEY_FOR_DB_SIZE_STUDYING_BIBLE, 0L);
                nullData.put(MainActivity.KEY_FOR_DB_SIZE_VIDEOS, 0L);
                nullData.put(MainActivity.KEY_FOR_DB_SIZE_REPEAT_VISITS, 0L);
                dataUsersInSingleСollection.put("group_warder", nullData);
                firebaseDatabase.getReference("sobranies")
                        .child(task.getResult().getUser().getUid())
                        .child(Calendar.getInstance().get(Calendar.YEAR) + "")
                        .child(Calendar.getInstance().get(Calendar.MONTH) + "")
                        .setValue(dataUsersInSingleСollection)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {
                                    setResult(Activity.RESULT_OK);
                                    outputViewShowGroupId.setText(firebaseAuth.getCurrentUser().getUid());
                                    Toast.makeText(RegisterWarderActivity.this, RegisterWarderActivity.this.getString(R.string.registration_successfully), Toast.LENGTH_LONG).show();                                }
                            }
                        });
            } else {
                Toast.makeText(RegisterWarderActivity.this, RegisterWarderActivity.this.getString(R.string.registration_not_successfully), Toast.LENGTH_LONG).show();
            }
        }
    };

    private OnCompleteListener<AuthResult> onCompleteListenerLogin = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                if (task.isComplete()) {
                    setResult(Activity.RESULT_OK);
                    outputViewShowGroupId.setText(firebaseAuth.getCurrentUser().getUid());
                    Toast.makeText(RegisterWarderActivity.this, RegisterWarderActivity.this.getString(R.string.registration_successfully), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(RegisterWarderActivity.this, RegisterWarderActivity.this.getString(R.string.registration_not_successfully), Toast.LENGTH_LONG).show();
            }
        }
    };
}


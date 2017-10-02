package com.example.reports;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
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

public class RegisterWarderActivity extends BaseActivity {
    private EditText etLogin;
    private EditText edPassword;
    @Inject
     FirebaseAuth firebaseAuth;
    @Inject
     FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_warder);
        etLogin = (EditText) findViewById(R.id.login_warder);
        edPassword = (EditText) findViewById(R.id.password_warder);
    }

    @Override
    public void injectDependencies() {
        getActivityComponent().inject(this);
    }

    public void registration(View v) {
        String login = etLogin.getText().toString();
        String password = edPassword.getText().toString();
        if (password.isEmpty() || login.isEmpty()) {
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(onCompleteListenerRegister);
    }

    public void login(View v) {
        String login = etLogin.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
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
                Map<String, String> dataUsersInSingleСollection = new HashMap<>();
                dataUsersInSingleСollection.put("group_warder", task.getResult().getUser().getUid());
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
                                    finish();
                                }
                            }
                        });
            } else {
                Toast.makeText(RegisterWarderActivity.this, " register not succhesfull", Toast.LENGTH_LONG).show();
            }
        }
    };

    private OnCompleteListener<AuthResult> onCompleteListenerLogin = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                if (task.isComplete()) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            } else {
                Toast.makeText(RegisterWarderActivity.this, " login not succhesfull", Toast.LENGTH_LONG).show();
            }
        }
    };
}


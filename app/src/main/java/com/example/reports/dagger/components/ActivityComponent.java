package com.example.reports.dagger.components;

import android.content.SharedPreferences;

import com.example.reports.ChooseDataReportActivity;
import com.example.reports.MainActivity;
import com.example.reports.RegisterUserActivity;
import com.example.reports.RegisterWarderActivity;
import com.example.reports.dagger.PerActivity;
import com.example.reports.dagger.modules.ActivityModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Component;

@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
@PerActivity
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(RegisterUserActivity activity);

    void inject(RegisterWarderActivity activity);

    void inject(ChooseDataReportActivity activity);

    //    FirebaseUser firebaseUser();
    FirebaseAuth firebaseAuth();

    FirebaseDatabase firebaseDatabase();

    SharedPreferences sharedPreferences();

}

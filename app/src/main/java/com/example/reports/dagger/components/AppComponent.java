package com.example.reports.dagger.components;

import android.content.SharedPreferences;

import com.example.reports.CurrentApplication;
import com.example.reports.dagger.modules.AppModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(CurrentApplication currentApplication);

//    FirebaseUser firebaseUser();

    FirebaseDatabase firebaseDatabase();

    FirebaseAuth firebaseAuth();

    SharedPreferences sharedPreferences();

}

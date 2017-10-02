package com.example.reports.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Олег on 02.10.2017.
 */

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    public Context provideContext(){
        return application;
    }

    @Provides
    public FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @Provides
    public FirebaseDatabase provideFirebaseDatabase(){
        return FirebaseDatabase.getInstance();
    }

//    @Provides
//    public FirebaseUser provideCurrentFirebaseUser(FirebaseAuth firebaseAuth){
//        return firebaseAuth.getCurrentUser();
//    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}

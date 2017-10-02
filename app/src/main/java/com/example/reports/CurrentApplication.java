package com.example.reports;

import android.app.Application;
import android.content.Context;

import com.example.reports.dagger.components.AppComponent;
import com.example.reports.dagger.components.DaggerAppComponent;
import com.example.reports.dagger.modules.AppModule;

public class CurrentApplication extends Application {
    public AppComponent appComponent;

    public static CurrentApplication get(Context context) {
        return (CurrentApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}

package com.example.reports;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.reports.dagger.components.ActivityComponent;
import com.example.reports.dagger.components.DaggerActivityComponent;

/**
 * Created by Олег on 02.10.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(CurrentApplication.get(this).getAppComponent())
                .build();
        injectDependencies();
    }

    public abstract void injectDependencies();

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}

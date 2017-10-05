package com.example.reports;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.reports.dagger.components.ActivityComponent;
import com.example.reports.dagger.components.DaggerActivityComponent;

import butterknife.ButterKnife;

/**
 * Created by Олег on 02.10.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(CurrentApplication.get(this).getAppComponent())
                .build();
        injectDependencies();
    }

    protected abstract int getLayoutId();

    public abstract void injectDependencies();

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}

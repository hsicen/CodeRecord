package com.hsc.vince.calendardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alamkanak.weekview.WeekViewNew;

public class MainActivity extends AppCompatActivity {

    private WeekViewNew mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeekView = findViewById(R.id.wvNew);

    }
}

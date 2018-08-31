package com.example.farhan.pushsum.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.farhan.pushsum.R;
import com.example.farhan.pushsum.entity.Week;
import com.example.farhan.pushsum.sqlhelper.TrainingDataSource;

public class WelcomesplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    SharedPreferences preferences;
    boolean first, db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomesplash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences = getSharedPreferences("prefs", MODE_PRIVATE);
                first = preferences.getBoolean("firstStart", true);
                db = preferences.getBoolean("firstDB", true);

                if (db) {

                    initializeDB();
                }

                if (first) {
                    toTutorial();
                } else {
                    toHome();
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void initializeDB() {
        TrainingDataSource tds = new TrainingDataSource(this);
        tds.open();
        tds.insert(new Week());
        tds.close();

        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstDB", false);
        editor.apply();
    }

    public void toTutorial() {
        Intent intent = new Intent(WelcomesplashActivity.this, TutorialActivity.class);
        startActivity(intent);

        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    public void toHome() {
        Intent intent = new Intent(WelcomesplashActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}

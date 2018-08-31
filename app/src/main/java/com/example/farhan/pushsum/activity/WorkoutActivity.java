package com.example.farhan.pushsum.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.farhan.pushsum.R;
import com.example.farhan.pushsum.entity.Week;
import com.example.farhan.pushsum.sqlhelper.TrainingDataSource;

import java.util.Calendar;


public class WorkoutActivity extends AppCompatActivity implements SensorEventListener {

    TextView textView, txt_goal_layer, txt_record_layer;
    CardView cardViewSave;


    TrainingDataSource tds;
    Week thisWeek;

    SensorManager sensorManager;
    Sensor sensor;

    SharedPreferences preferences;
    boolean firstToWork;

    int pushs, obbiettivo, massimo, today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        textView = (TextView) findViewById(R.id.txt_pushUp_counter);
        txt_goal_layer = (TextView) findViewById(R.id.txt_goal_layer);
        txt_record_layer = (TextView) findViewById(R.id.txt_personal_record_layer);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        tds = new TrainingDataSource(this);
        tds.open();
        thisWeek = tds.getWeek();
        tds.close();

        getTodaysPushUps();

        cardViewSave = (CardView) findViewById(R.id.card_view_save);
        cardViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tds.open();
                tds.delete(thisWeek);
                setTodaysPushUps();
                tds.insert(thisWeek);
                tds.close();

                goToNext();
            }
        });
    }

    private void goToNext() {
        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        firstToWork = preferences.getBoolean("firstToWork", true);

        if (firstToWork) {
            goToHome();
        }
        finish();

    }

    private void goToHome() {
        Intent intent = new Intent(WorkoutActivity.this, HomeActivity.class);
        startActivity(intent);

        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstToWork", false);
        editor.apply();
    }

    private void setTodaysPushUps() {
        if (today == Calendar.MONDAY) {
            thisWeek.setMonday(pushs);
        } else if (today == Calendar.TUESDAY) {
            thisWeek.setTuesday(pushs);
        } else if (today == Calendar.WEDNESDAY) {
            thisWeek.setWednesday(pushs);
        } else if (today == Calendar.THURSDAY) {
            thisWeek.setThursday(pushs);
        } else if (today == Calendar.FRIDAY) {
            thisWeek.setFriday(pushs);
        } else if (today == Calendar.SATURDAY) {
            thisWeek.setSaturday(pushs);
        } else if (today == Calendar.SUNDAY) {
            thisWeek.setSunday(pushs);
        }
        if (pushs > thisWeek.getMassimo()) {
            thisWeek.setMassimo(pushs);
        }
    }

    private void getTodaysPushUps() {
        Calendar calendar = Calendar.getInstance();
        today = calendar.get(Calendar.DAY_OF_WEEK);

        if (today == Calendar.MONDAY) {
            pushs = thisWeek.getMonday();
        } else if (today == Calendar.TUESDAY) {
            pushs = thisWeek.getTuesday();
        } else if (today == Calendar.WEDNESDAY) {
            pushs = thisWeek.getWednesday();
        } else if (today == Calendar.THURSDAY) {
            pushs = thisWeek.getThursday();
        } else if (today == Calendar.FRIDAY) {
            pushs = thisWeek.getFriday();
        } else if (today == Calendar.SATURDAY) {
            pushs = thisWeek.getSaturday();
        } else if (today == Calendar.SUNDAY) {
            pushs = thisWeek.getSunday();
        }


        obbiettivo = thisWeek.getObbiettivo();
        massimo = thisWeek.getMassimo();

        txt_goal_layer.setText("Goal: " + obbiettivo);
        txt_record_layer.setText("Personal record: " + massimo);
        textView.setText(pushs + "");

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] == 0.0) {
                pushs++;
            }
            textView.setText(pushs + "");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

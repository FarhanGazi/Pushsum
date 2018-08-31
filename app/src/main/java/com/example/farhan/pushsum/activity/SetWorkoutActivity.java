package com.example.farhan.pushsum.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farhan.pushsum.R;
import com.example.farhan.pushsum.entity.Week;
import com.example.farhan.pushsum.sqlhelper.TrainingDataSource;

public class SetWorkoutActivity extends AppCompatActivity {

    CardView card_view_saveEdit;
    ImageView increase, decrease;
    TextView txt_work_quantity;

    int actualWork;

    final int CONST_LEVEL_VAR = 5;

    Week thisWeek;
    TrainingDataSource tsd;

    SharedPreferences preferences;
    boolean firstToSetWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_workout);

        tsd = new TrainingDataSource(this);
        tsd.open();
        thisWeek = tsd.getWeek();
        actualWork = thisWeek.obbiettivo;
        tsd.close();

        card_view_saveEdit = (CardView) findViewById(R.id.card_view_saveEdit);
        txt_work_quantity = (TextView) findViewById(R.id.txt_work_quantity);
        increase = (ImageView) findViewById(R.id.img_increase_work);
        decrease = (ImageView) findViewById(R.id.img_decrease_work);

        setActualWork();

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualWork = actualWork + CONST_LEVEL_VAR;
                setActualWork();
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actualWork > CONST_LEVEL_VAR) {
                    actualWork = actualWork - CONST_LEVEL_VAR;
                    setActualWork();
                } else {
                    toastMessage("You have to do at least 5 push ups!");
                }
            }
        });

        card_view_saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tsd.open();
                tsd.delete(thisWeek);
                thisWeek.setObbiettivo(actualWork);
                tsd.insert(thisWeek);
                tsd.close();

                preferences = getSharedPreferences("prefs", MODE_PRIVATE);
                firstToSetWork = preferences.getBoolean("firstToSetWork", true);

                if (firstToSetWork) {
                    goToWorkout();
                }
                finish();
            }
        });

    }

    private void goToWorkout() {
        Intent intent = new Intent(SetWorkoutActivity.this, WorkoutActivity.class);
        startActivity(intent);
        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstToSetWork", false);
        editor.apply();
        toastMessage("Do your first workout!");
    }

    public void toastMessage(String msg) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void setActualWork() {
        txt_work_quantity.setText(actualWork + "");
    }
}

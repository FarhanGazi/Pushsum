package com.example.farhan.pushsum.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farhan.pushsum.R;
import com.example.farhan.pushsum.entity.Week;
import com.example.farhan.pushsum.sqlhelper.TrainingDataSource;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    ImageView image_info;
    CardView card_view_work, card_view_set_work, card_view_statistics;
    TextView pushUpsQuantity, toDoQuantity, toDoLayer;

    int today, pushs, obbiettivo, massimo, extra;

    final int WORKACTIVITYCODE = 1, SETWORKACTIVITYCODE = 2;

    TrainingDataSource tds;
    Week thisWeek;

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         *
         */
        image_info = (ImageView) findViewById(R.id.image_info);
        image_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

        /**
         *
         */
        card_view_work = (CardView) findViewById(R.id.card_view_work);
        card_view_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, WorkoutActivity.class);
                startActivityForResult(intent, WORKACTIVITYCODE);
            }
        });

        /**
         *
         */
        card_view_set_work = (CardView) findViewById(R.id.card_view_setWork);
        card_view_set_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SetWorkoutActivity.class);
                startActivityForResult(intent, SETWORKACTIVITYCODE);
            }
        });

        card_view_statistics = (CardView) findViewById(R.id.card_view_statistics);
        card_view_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });


        pushUpsQuantity = (TextView) findViewById(R.id.pushUpsQuantity);
        toDoQuantity = (TextView) findViewById(R.id.toDoQuantity);
        toDoLayer = (TextView) findViewById(R.id.toDolayer);
        pieChart = (PieChart) findViewById(R.id.piechart);

        setUpdate();
    }

    private void openDbConnection() {
        tds = new TrainingDataSource(this);
        tds.open();
        thisWeek = tds.getWeek();
        tds.close();
    }

    private void setUpdate() {
        openDbConnection();
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

        fixLayout();
    }

    private void fixLayout() {
        pushUpsQuantity.setText(pushs + "");

        if (pushs > obbiettivo) {
            extra = pushs - obbiettivo;
            toDoLayer.setText("extra");
        } else {
            extra = obbiettivo - pushs;
            toDoLayer.setText("to do");
        }
        toDoQuantity.setText(extra + "");

        makeChart();
    }

    private void makeChart() {
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);

        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        float lableVal = getDone();
        if (lableVal > 0) yValues.add(new PieEntry(lableVal, "Done"));

        lableVal = getToDo();
        if (lableVal > 0) yValues.add(new PieEntry(lableVal, "To be done"));

        lableVal = getExtraPushs();
        if (lableVal > 0) yValues.add(new PieEntry(lableVal, "Extra"));

        PieDataSet dataSet = new PieDataSet(yValues, "About today");

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.createColors(new int[]{Color.argb(255, 46, 125, 50),
                Color.argb(255, 223, 120, 25)
        }));

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == WORKACTIVITYCODE) || (requestCode == SETWORKACTIVITYCODE)) {
            setUpdate();
        }
    }


    public float getDone() {
        int max = getMaxPush();
        return (pushs * 1f / max) * 100f;
    }

    public float getToDo() {
        int max = getMaxPush();
        return pushs < obbiettivo ? (extra * 1f / max) * 100f : 0f;
    }

    public float getExtraPushs() {
        int max = getMaxPush();
        return pushs > obbiettivo ? ((extra) * 1f / max) * 100f : 0f;
    }

    public int getMaxPush() {
        int maxPush = 0;
        if (pushs > maxPush) maxPush = pushs;
        if (obbiettivo > maxPush) maxPush = obbiettivo;
        if (massimo > maxPush) maxPush = massimo;
        return maxPush;
    }
}

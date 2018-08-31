package com.example.farhan.pushsum.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.farhan.pushsum.R;
import com.example.farhan.pushsum.entity.Week;
import com.example.farhan.pushsum.sqlhelper.TrainingDataSource;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    BarChart barChart;

    TrainingDataSource tds;
    Week thisWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        createChart();
    }

    private void openDbConnection() {
        tds = new TrainingDataSource(this);
        tds.open();
        thisWeek = tds.getWeek();
        tds.close();
        System.out.println(thisWeek);
    }

    private void createChart() {

        openDbConnection();

        barChart = (BarChart) findViewById(R.id.barChart);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(thisWeek.massimo);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, thisWeek.getMonday() * 1f));
        barEntries.add(new BarEntry(2, thisWeek.getTuesday() * 1f));
        barEntries.add(new BarEntry(3, thisWeek.getWednesday() * 1f));
        barEntries.add(new BarEntry(4, thisWeek.getThursday() * 1f));
        barEntries.add(new BarEntry(5, thisWeek.getFriday() * 1f));
        barEntries.add(new BarEntry(6, thisWeek.getSaturday() * 1f));
        barEntries.add(new BarEntry(7, thisWeek.getSunday() * 1f));

        BarDataSet barDataSet = new BarDataSet(barEntries, "About this week");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.6f);

        barChart.setData(data);

        String[] months = new String[]{"", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "", ""};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValuesFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(false);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setGranularity(1f);

        yAxis = barChart.getAxisRight();
        yAxis.setEnabled(false);

        Description description = new Description();
        description.setText("");

        barChart.setDescription(description);

    }

    public class MyXAxisValuesFormatter implements IAxisValueFormatter {

        private String[] nValues;

        public MyXAxisValuesFormatter(String[] nValues) {
            this.nValues = nValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return nValues[(int) value];
        }
    }


}

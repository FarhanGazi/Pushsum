package com.example.farhan.pushsum.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farhan.pushsum.R;
import com.example.farhan.pushsum.adapter.SliderAdapter;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager nSlideViewPager;
    private LinearLayout nDotLayout;

    private SliderAdapter sliderAdapter;

    private TextView[] nDots;

    private AppCompatButton next;
    private AppCompatButton prec;
    private int currentPage;

    SharedPreferences preferences;
    boolean firstToTut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        nSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        nDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        next = findViewById(R.id.button_next);
        prec = findViewById(R.id.button_prec);

        sliderAdapter = new SliderAdapter(this);
        nSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        nSlideViewPager.addOnPageChangeListener(viewListener);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferences = getSharedPreferences("prefs", MODE_PRIVATE);
                firstToTut = preferences.getBoolean("firstToTut", true);

                if (currentPage == 2) {

                    if (firstToTut) {
                        goToSetWork();
                    }
                    finish();
                }
                nSlideViewPager.setCurrentItem(currentPage + 1);
            }
        });

        prec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nSlideViewPager.setCurrentItem(currentPage - 1);
            }
        });
    }

    private void goToSetWork() {

        Intent intent = new Intent(TutorialActivity.this, SetWorkoutActivity.class);
        startActivity(intent);

        Toast.makeText(this,"Set your first goal!",Toast.LENGTH_SHORT).show();

        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstToTut", false);
        editor.apply();
    }

    public void addDotsIndicator(int position) {
        nDots = new TextView[3];
        nDotLayout.removeAllViews();
        for (int i = 0; i < nDots.length; i++) {
            nDots[i] = new TextView(this);
            nDots[i].setText(Html.fromHtml("&#8226;"));
            nDots[i].setTextSize(35);
            nDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            nDotLayout.addView(nDots[i]);

        }

        if (nDots.length > 0) {
            nDots[position].setTextColor(getResources().getColor(R.color.colorPosition));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        public void onPageSelected(int position) {
            addDotsIndicator(position);

            currentPage = position;

            if (position == 0) {
                next.setEnabled(true);
                prec.setEnabled(false);
                prec.setVisibility(View.INVISIBLE);

                next.setText("Next");
                prec.setText("");
            } else if (position == nDots.length - 1) {
                next.setEnabled(true);
                prec.setEnabled(true);
                prec.setVisibility(View.VISIBLE);

                next.setText("Finish");
                prec.setText("Back");
            } else {
                next.setEnabled(true);
                prec.setEnabled(true);
                prec.setVisibility(View.VISIBLE);

                next.setText("Next");
                prec.setText("Back");
            }
        }

        public void onPageScrollStateChanged(int state) {

        }

    };
}

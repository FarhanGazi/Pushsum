package com.example.farhan.pushsum.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.farhan.pushsum.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public int[] slide_images = {
            R.drawable.ic_workout,
            R.drawable.ic_trophy,
            R.drawable.ic_chart
    };

    public String[] slide_headings = {
            "Workout",
            "Achievement",
            "Statistics"
    };

    public String[] slide_descriptions = {
            "Workout",
            "Achievement",
            "Statistics"
    };

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_slider, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_description);

        //Tmage
        slideImageView.setImageResource(slide_images[position]);

        //Heading
        slideHeading.setText(slide_headings[position]);
        slideHeading.setTextColor(Color.GRAY);
        slideHeading.setGravity(Gravity.CENTER);

        //Description
        slideDescription.setText(slide_descriptions[position]);
        slideDescription.setTextColor(Color.GRAY);
        slideDescription.setGravity(Gravity.CENTER);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

}

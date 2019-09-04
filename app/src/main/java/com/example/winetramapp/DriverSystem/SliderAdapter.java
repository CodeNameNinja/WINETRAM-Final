package com.example.winetramapp.DriverSystem;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.winetramapp.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;

    SliderAdapter(Context context){
        this.context = context;
    }

    private int[] slide_image = {
        R.drawable.bus_with_transparent_circle,
        R.drawable.bus_with_transparent_circle,
        R.drawable.bus_with_transparent_circle,
        R.drawable.bus_with_transparent_circle,
        R.drawable.bus_with_transparent_circle,
        R.drawable.bus_with_transparent_circle,
        R.drawable.bus_with_transparent_circle,
        R.drawable.bus_with_transparent_circle,
        R.drawable.tram_with_transparent_circle,
        R.drawable.tram_with_transparent_circle
    };

    private String[] slide_heading = {
            "Red Line",
            "Blue Line",
            "Green Line",
            "Yellow Line",
            "Orange Line",
            "Purple Line",
            "Pink Line",
            "Grey Line",
            "Tram Franschhoek",
            "Tram Drakenstein"
    };

//    private String[] slide_desc = {
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
//            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et"
//
//    };

    private int[] hexcolor = {
            Color.rgb(222, 78, 78),
            Color.rgb(78, 150, 222),
            Color.rgb(78, 222, 88),
            Color.rgb(222, 217, 78),
            Color.rgb(222, 140, 78),
            Color.rgb(124, 78, 222),
            Color.rgb(222, 78, 179),
            Color.rgb(133, 130, 132),
            Color.rgb(222, 78, 78),
            Color.rgb(124, 78, 222)

    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slider_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slider_header);
//        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);
        CardView slide_color = (CardView) view.findViewById(R.id.slider_card);

        slideImageView.setImageResource(slide_image[position]);
        slideHeading.setText(slide_heading[position]);
//        slideDescription.setText(slide_desc[position]);
        slide_color.setCardBackgroundColor(hexcolor[position]);

        container.addView(view);

        return view;
    }
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((ConstraintLayout)object);
    }
}

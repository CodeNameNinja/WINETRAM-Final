package com.example.winetramapp.UserSystem;


import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.winetramapp.DriverSystem.DriverLoginActivity;
import com.example.winetramapp.R;

import java.util.ArrayList;
import java.util.List;

public class SelectRoute extends AppCompatActivity {

    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private LinearLayout mDotsLayout;
    private static int mCurrentPage;
    private TextView[] mDots;
    public static int selectedRoute;

    private void goToUserScreen()
    {
        Intent intent = new Intent(this,UserScreen.class);
        startActivity(intent);
    }
    public void selectLine(View view)
    {
        switch(mCurrentPage)
        {
            case 0:
               selectedRoute = 0;
               goToUserScreen();
                break;
            case 1:
               selectedRoute = 1;
               goToUserScreen();
                break;
            case 2:
                selectedRoute = 2;
                goToUserScreen();
                break;
            case 3:
                selectedRoute = 3;
                goToUserScreen();
                break;
            case 4:
                selectedRoute = 4;
                goToUserScreen();
                break;
            case 5:
                selectedRoute = 5;
                goToUserScreen();
                break;
            case 6:
                selectedRoute = 6;
                goToUserScreen();
                break;
            case 7:
                selectedRoute = 7;
                goToUserScreen();
                break;

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_route_activity);

        getSupportActionBar().setTitle("Select a Route");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDotsLayout = (LinearLayout) findViewById(R.id.userDotsLayout);

        models = new ArrayList<>();
        models.add(new Model(R.drawable.grande_provance, "Red Line", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        models.add(new Model(R.drawable.grande_provance, "Blue Line", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        models.add(new Model(R.drawable.grande_provance, "Green Line", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        models.add(new Model(R.drawable.grande_provance, "Yellow Line", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        models.add(new Model(R.drawable.grande_provance, "Purple Line", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        models.add(new Model(R.drawable.grande_provance, "Orange Line", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        models.add(new Model(R.drawable.grande_provance, "Grey Line", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        models.add(new Model(R.drawable.grande_provance, "Pink Line", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.grey),
                getResources().getColor(R.color.pink),
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int i) {
                addDotsIndicator(i);
                mCurrentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        addDotsIndicator(0);
    }

    public void addDotsIndicator(int position)
    {
        mDots = new TextView[8];
        mDotsLayout.removeAllViews();
        for(int i = 0; i < mDots.length; i++)
        {
            mDots[i] = new TextView(SelectRoute.this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotsLayout.addView(mDots[i]);
        }

        if(mDots.length > 0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

}
package com.example.winetramapp.UserSystem.Routes;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winetramapp.R;
import com.example.winetramapp.UserSystem.RecyclerAdapter;
import com.example.winetramapp.UserSystem.RecyclerItem;
import com.example.winetramapp.UserSystem.SelectRoute;

import java.util.ArrayList;

public class OrangeLineScreen extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean switchOnOff;
    String TAG = OrangeLineScreen.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen_activity);

        getSupportActionBar().setTitle("Time Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<RecyclerItem> mList = new ArrayList<>();

        if(SelectRoute.selectedRoute == 5) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
            mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "9:30 | 10:05 | 10:40 | 11:15 | 11:50"));
            mList.add(new RecyclerItem(R.drawable.bus, "Babylonstoren", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Backsberg", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Boschendal", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.tram, "Vrede en Lust", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.tram, "Plasir de Merle", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Allee Blue", "10:35 | 11:35 | 12:35 | 13:35 | 14:35 | 15:35 | 16:35"));
            mList.add(new RecyclerItem(R.drawable.bus, "Solms Delta", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));
            mList.add(new RecyclerItem(R.drawable.bus, "Boschendal", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 12:34 | 15:09 | 15:44 | 16:19"));

        }
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Switch notificationSwitch = (Switch) findViewById(R.id.notification_switch);
        loadData("orange");
        Log.i(TAG,"Loaded orange");
        updateView(notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveData("orange",b);
                Log.i(TAG,"saved orange");
            }
        });
    }

    private void saveData(String key , boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public void loadData(String key)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(key,false);
    }
    public void updateView(Switch selectedLineSwitch)
    {
        try{
            selectedLineSwitch.setChecked(switchOnOff);
        }catch (Exception e)
        {
            Log.e("Error", "did not update View");
        }

    }
}

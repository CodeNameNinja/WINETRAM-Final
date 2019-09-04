package com.example.winetramapp.UserSystem;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winetramapp.R;

import java.util.ArrayList;

public class UserScreen extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setTitle("Time Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<RecyclerItem> mList = new ArrayList<>();
        switch(SelectRoute.selectedRoute)
        {
            case 0:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
                mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "10:30 | 11:30"));
                mList.add(new RecyclerItem(R.drawable.bus, "Maison", "10:39 | 11:39 | 12:39 | 13:39 | 14:39 | 15:39 16:39"));
                mList.add(new RecyclerItem(R.drawable.bus, "Mont Rochelle", "10:51 | 11:51 | 12:51 | 13:51 | 14:51 | 15:51 | 16:51"));
                mList.add(new RecyclerItem(R.drawable.bus, "Holden Manz", "11:02 | 12:02 | 13:02 | 14:02 | 15:02 | 16:02 | 17:02"));
                mList.add(new RecyclerItem(R.drawable.bus, "Charmonix", "11:14 | 12:14 | 13:14 | 14:14 | 15:14 | 16:14 | 17:14"));
                mList.add(new RecyclerItem(R.drawable.bus, "Dieu Donne", "11:17 | 12:17 | 13:17 | 14:17 | 15:17 | 16:17 | 17:17"));
                mList.add(new RecyclerItem(R.drawable.tram, "Grande Provance Platform", "-- | -- | 13:33 | 14:33 | 15:33 | 16:33 | 17:33"));
                mList.add(new RecyclerItem(R.drawable.tram, "Rickety Bridge", "-- | -- | 13:41 | 14:41 | 15:41 | 16:41 | 17:41"));
                break;

            case 1:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
                mList.add(new RecyclerItem(R.drawable.tram, "Ticket Office", "10:45 | 11:45 | 12:45"));
                mList.add(new RecyclerItem(R.drawable.tram, "Grande Provance", "11:03 | 12:03 | 13:03 | 14:03 | 15:03 | 16:03"));
                mList.add(new RecyclerItem(R.drawable.tram, "Rickety Bridge", "11:11 | 12:11 | 13:11 | 14:11 | 15:11 | 16:11"));
                mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Village", "-- | 12:30 | 13:30 | 14:30 | 15:30 | 16:30"));
                mList.add(new RecyclerItem(R.drawable.bus, "Maison", "-- | 12:39 | 13:39 | 14:39 | 15:39 | 16:39"));
                mList.add(new RecyclerItem(R.drawable.bus, "Mont Rochelle", "-- | 12:51 | 13:51 | 14:51 | 15:51 | 16:51 | 17:51"));
                mList.add(new RecyclerItem(R.drawable.bus, "Holden Manz", "-- | 13:02 | 14:02 | 15:02 | 16:02 | 17:02"));
                mList.add(new RecyclerItem(R.drawable.bus, "Charmonix", "-- | -- | 13:14 | 14:14 | 15:14 | 16:14 | 17:14"));
                mList.add(new RecyclerItem(R.drawable.bus, "Dieu Donne", "-- | -- | 13:17 | 14:17 | 15:17 | 16:17 | 17:17"));

                break;
            case 2:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
                mList.add(new RecyclerItem(R.drawable.tram, "Ticket Office", "10:15 | 11:15 | 12:15"));
                mList.add(new RecyclerItem(R.drawable.tram, "Grande Provance", "10:33 | 11:33 | 12:33 | 13:33 | 14:33 | 15:33 | 16:33"));
                mList.add(new RecyclerItem(R.drawable.tram, "Rickety Bridge", "10:41 | 11:41 | 12:41 | 13:41 | 14:41 | 15:41 16:41"));
                mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Village", "-- | 12:00 | 13:00 | 14:00 | 15:00 | 16:00 | 17:00"));
                mList.add(new RecyclerItem(R.drawable.bus, "La Bri", "-- | 12:07 | 13:07 | 14:07 | 15:07 | 16:07 | 17:07"));
                mList.add(new RecyclerItem(R.drawable.bus, "Holden Manz", "-- | 12:15 | 13:15 | 14:15 | 15:15 | 16:15 | 17:15"));
                mList.add(new RecyclerItem(R.drawable.bus, "La Bourgogne", "-- | 12:22 | 13:22 | 14:22 | 15:22 | 16:22 | 17:22"));
                mList.add(new RecyclerItem(R.drawable.bus, "Glenwood", "-- | -- | 12:35 | 13:35 | 14:35 | 15:35 | 16:35 | 17:35"));
                mList.add(new RecyclerItem(R.drawable.bus, "La Couronne", "-- | -- | 12:46 | 13:46 | 14:46 | 15:46 | 16:46 | 17:46"));

                break;
            case 3:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yellow)));
                mList.add(new RecyclerItem(R.drawable.tram, "Ticket Office", "10:45 | 11:45 | 12:45"));
                mList.add(new RecyclerItem(R.drawable.tram, "Grande Provance", "11:03 | 12:03 | 13:03 | 14:03 | 15:03 | 16:03"));
                mList.add(new RecyclerItem(R.drawable.tram, "Rickety Bridge", "11:11 | 12:11 | 13:11 | 14:11 | 15:11 | 16:11"));
                mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Village", "-- | 12:30 | 13:30 | 14:30 | 15:30 | 16:30"));
                mList.add(new RecyclerItem(R.drawable.bus, "Maison", "-- | 12:39 | 13:39 | 14:39 | 15:39 | 16:39"));
                mList.add(new RecyclerItem(R.drawable.bus, "Mont Rochelle", "-- | 12:51 | 13:51 | 14:51 | 15:51 | 16:51 | 17:51"));
                mList.add(new RecyclerItem(R.drawable.bus, "Holden Manz", "-- | 13:02 | 14:02 | 15:02 | 16:02 | 17:02"));
                mList.add(new RecyclerItem(R.drawable.bus, "Charmonix", "-- | -- | 13:14 | 14:14 | 15:14 | 16:14 | 17:14"));
                mList.add(new RecyclerItem(R.drawable.bus, "Dieu Donne", "-- | -- | 13:17 | 14:17 | 15:17 | 16:17 | 17:17"));

                break;
            case 4:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));
                mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "9:30 | 10:05 | 10:40 | 11:15 | 11:50"));
                mList.add(new RecyclerItem(R.drawable.bus, "Allee Bleue", "9:54 | 10:29 | 11:04 | 11:39 | 12:14 | 12:49 | 13:24 | 13:59 | 14:34 | 15:09 | 15:44 | 16:19"));
                mList.add(new RecyclerItem(R.drawable.bus, "Solms Delta", "10:00| 10:35 | 11:10 | 11:45 | 12:20 | 12:55 | 13:30 | 14:05 | 14:40 | 15:15 | 15:50 | 16:25"));
                mList.add(new RecyclerItem(R.drawable.bus, "Boschendal", "10:12 | 10:47 | 11:22 | 11:57 | 12:32 | 13:07 | 13:42 | 14:17 | 14:52 | 15:27 | 16:02 | 16:37"));
                mList.add(new RecyclerItem(R.drawable.tram, "Plasir De Merle", "10:31 | 11:06 | 11:41 | 12:16 | 12:51 | 13:26 | 14:01 | 14:36 | 15:11 | 15:46 | 16:21 | 16:56"));
                mList.add(new RecyclerItem(R.drawable.tram, "Vrede en Lust", "10:35 | 11:10 | 11:45 | 12:20 | 12:55 | 13:30 | 14:05 | 14:40 | 15:50 | 16:25 | 17:00"));
                mList.add(new RecyclerItem(R.drawable.bus, "Nobile Hill", "--- | --- | --- | 12:31 | 13:06 | 13:41 | 14:16 | 14:51 | 15:26 | 16:01 | 16:36 | 17:11"));
                mList.add(new RecyclerItem(R.drawable.bus, "Babylonstoren", "--- | --- | --- | 12:39 | 13:14 | 13:49 | 14:24 | 14:59 | 15:34 | 16:09 | 16:44 | 17:19"));
                mList.add(new RecyclerItem(R.drawable.bus, "Backsberg", "--- | --- | --- | 12:46 | 13:21 | 13:56 | 14:31 | 15:06 | 15:34 | 16:16 | 16:51 | 17:26"));

                break;
            case 5:
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

                break;
            case 6:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.grey)));
                mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "10:00 | 10:30 | 11:00 | 11:30 | 12:00"));
                mList.add(new RecyclerItem(R.drawable.bus, "Haute Cabriere", "10:06 | 10:36 | 11:06 | 11:36 | 12:06 | 12:36 | 13:06 | 13:36 | 14:06 | 14:36 | 15:06 | 15:36 | 16:06 | 16:36"));
                mList.add(new RecyclerItem(R.drawable.bus, "Le Lude", "10:11 | 10:41 | 11:11 | 11:41 | 12:11 | 12:41 | 13:11 | 13:41 | 14:11 | 14:41 | 15:11 | 15:41 | 16:11 | 16:41"));
                mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Celler", "10:18 | 10:48 | 11:18 | 11:48 | 12:18 | 12:48 | 13:18 | 13:48 | 14:18 | 14:48 | 15:18 | 15:48 | 16:18 | 16:48"));
                mList.add(new RecyclerItem(R.drawable.tram,"Tram Platform", "--- | --- | 11:23 | 11:53 | 12:23 | 12:53 | 13:23 | 13:53 | 14:23 | 14:54 | 15:23 | 15:54| 16:23 | 16:53"));
                mList.add(new RecyclerItem(R.drawable.bus, "Leapard's Leap", "--- | --- | 11:42 | 14:03 | 15:03 | 16:03 | 17:03"));
                mList.add(new RecyclerItem(R.drawable.bus, "Moreson", "--- | --- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
                mList.add(new RecyclerItem(R.drawable.bus, "Eikehof", "--- | --- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
                mList.add(new RecyclerItem(R.drawable.bus, "Paserene", "--- | --- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));

                break;
            case 7:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));

                mList.add(new RecyclerItem(R.drawable.bus, "Ticket Office", "10:07 | 11:07 | 12:07 | 13:07 | 14:07 | 15:07 | 16:07"));
                mList.add(new RecyclerItem(R.drawable.tram, "Tram Platform", "10:15 | 11:15 | 12:15 | 13:15 | 14:15 | 15:15 16:15"));
                mList.add(new RecyclerItem(R.drawable.bus, "Leopards Leap", "10:22 | 11:22 | 12:22 | 13:22 | 14:22 | 15:22 | 16:22"));
                mList.add(new RecyclerItem(R.drawable.bus, "Moreson", "10:35 | 11:35 | 12:35 | 13:35 | 14:35 | 15:35 | 16:35"));
                mList.add(new RecyclerItem(R.drawable.bus, "Eikehof", "10:46| 11:46 | 12:46 | 13:46 | 14:46 | 15:46 | 16:46"));
                mList.add(new RecyclerItem(R.drawable.bus, "Paserene", "-- | -- | 13:03 | 14:03 | 15:03 | 16:03 | 17:03"));
                mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Village", "-- | -- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
                mList.add(new RecyclerItem(R.drawable.bus, "Haute Cabriere", "-- | -- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
                mList.add(new RecyclerItem(R.drawable.bus, "Le Lude", "-- | -- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));
                mList.add(new RecyclerItem(R.drawable.bus, "Franschhoek Celler", "-- | -- | 13:11 | 14:11 | 15:11 | 16:11 | 17:11"));

                break;

        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(mList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


}

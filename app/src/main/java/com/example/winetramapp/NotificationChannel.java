package com.example.winetramapp;

import android.app.Application;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationChannel extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        creatNotificationChannels();
    }

    public static final String CHANNEL_1_ID = "notify User of bus arrival";
    public static final String CHANNEL_2_ID = "notify User of Tram arrival";
    public static final String CHANNEL_3_ID = "notify User of Tram arrival";
    private void creatNotificationChannels()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            android.app.NotificationChannel channel1 = new android.app.NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel ",
                    NotificationManager.IMPORTANCE_HIGH
            );
            android.app.NotificationChannel channel2 = new android.app.NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel ",
                    NotificationManager.IMPORTANCE_HIGH
            );
            android.app.NotificationChannel channel3 = new android.app.NotificationChannel(
                    CHANNEL_3_ID,
                    "Channel ",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Bus is Arriving soon!");
            channel2.setDescription("Tram is Arriving soon!");
            channel3.setDescription("Tram is Arriving soon!");

            NotificationManager manger = getSystemService(NotificationManager.class);
            manger.createNotificationChannel(channel1);
            manger.createNotificationChannel(channel2);
            manger.createNotificationChannel(channel3);
        }
    }
}


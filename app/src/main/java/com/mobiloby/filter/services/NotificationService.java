package com.mobiloby.filter.services;

import android.content.Context;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationService extends NotificationListenerService {

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        // Send a braoadcast with some data
        Intent notificationMessage = new Intent("SomeData");
        notificationMessage.putExtra("Hello", "World");

        LocalBroadcastManager.getInstance(context).sendBroadcast(notificationMessage);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");

    }
}
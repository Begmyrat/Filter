package com.mobiloby.filter.helpers;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.mobiloby.filter.R;

public class RingtonePlayingService extends Service {

    MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Log.e("on Destroy called", "ta da");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = MediaPlayer.create(getApplicationContext(), R.raw.dog);
        mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mp.start();

        return START_NOT_STICKY;
    }


}

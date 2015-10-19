package com.tom.tester;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2015/10/19.
 */
public class HelloReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RECE", "onReceive");
        if (intent.getAction().equals("com.tom.tester.HELLO")){
            NotificationManager notiManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Notification noti = new Notification.Builder(context)
                    .setContentTitle("TOM")
                    .setContentText("Hello av8d")
                    .build();
            notiManager.notify(1, noti);
        }
    }
}

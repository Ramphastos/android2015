package com.tom.tester;

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

        }
    }
}

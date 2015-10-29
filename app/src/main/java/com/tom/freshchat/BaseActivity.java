package com.tom.freshchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2015/10/29.
 */
public class BaseActivity extends AppCompatActivity {
    private ChatApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ChatApplication)getApplication();
    }

    public ChatApplication getApp() {
        return app;
    }
}

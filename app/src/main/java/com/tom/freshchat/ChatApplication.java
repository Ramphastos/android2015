package com.tom.freshchat;

import android.app.Application;

/**
 * Created by Administrator on 2015/10/29.
 */
public class ChatApplication extends Application{
    private boolean logon = false;

    public boolean isLogon() {
        return logon;
    }

    public void setLogon(boolean logon) {
        this.logon = logon;
    }
}

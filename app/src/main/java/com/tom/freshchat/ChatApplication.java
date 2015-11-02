package com.tom.freshchat;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/10/29.
 */
public class ChatApplication extends Application{
    private boolean logon = false;
    private String uid = "";
    public boolean isLogon() {
        return logon;
    }

    public void setLogon(boolean logon) {
        this.logon = logon;
    }

    public String getUid() {
        uid = getSharedPreferences("chat", MODE_PRIVATE)
                .getString("uid", "");
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        getSharedPreferences("chat", MODE_PRIVATE)
                .edit()
                .putString("uid", uid)
                .commit();

    }
}

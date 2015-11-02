package com.tom.frag;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/11/2.
 */
public class Fragment1 extends Fragment{
    private static Fragment1 frag;
    public static Fragment1 getInstance(){
        if (frag == null){
            frag = new Fragment1();
        }
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment1, null);
    }
}

package com.tom.frag;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    Fragment1 f1;
    Fragment2 f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();

        Fragment fragment = null;
        if (display.getWidth()>display.getHeight()){
             fragment = Fragment2.getInstance();
        }else{
            fragment = Fragment1.getInstance();
        }
        trans.replace(R.id.content, fragment);
        trans.commit();

//        f1 = (Fragment1) getFragmentManager().findFragmentById(R.id.fragment);
//        f2 = (Fragment2) getFragmentManager().findFragmentById(R.id.fragment2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

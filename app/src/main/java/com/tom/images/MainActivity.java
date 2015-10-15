package com.tom.images;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        Log.d("SIZE", c.getCount()+"");
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(MediaStore.Images.Media._ID));
            String data = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
            String name = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            Log.d("IMG", id+"/"+data+"/"+name);
        }
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

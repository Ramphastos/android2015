package com.tom.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //read contacts
        ListView list = (ListView) findViewById(R.id.list);
        ContentResolver cr = getContentResolver();
        String[] projection = {ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                Phone.NUMBER
        };
        Cursor c = cr.query(Phone.CONTENT_URI,
                projection,
                null,
                null,
                null);
        String[] from = {ContactsContract.Contacts.DISPLAY_NAME, Phone.NUMBER};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_2, c, from, to, 1);

        list.setAdapter(adapter);
        /*
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(
                    ContactsContract.Contacts._ID));
            String name = c.getString(c.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));
            int hasPhone = c.getInt(c.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER));
            Log.d(TAG, id+"/"+name+"/"+hasPhone);
            if (hasPhone==1){
                Cursor c2 = cr.query(
                        Phone.CONTENT_URI,
                        null,
                        Phone.CONTACT_ID+"=?",
                        new String[]{id+""},
                        null);
                while(c2.moveToNext()){
                    String phone = c2.getString(c2.getColumnIndex(Phone.NUMBER));
                    Log.d(TAG, "  >"+phone);
                }
            }
        }*/

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

package com.tom.images;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        Log.d("SIZE", c.getCount()+"");

        /*while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(MediaStore.Images.Media._ID));
            String data = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
            String name = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            Log.d("IMG", id+"/"+data+"/"+name);
        }*/
        String[] from = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
        int[] to = {R.id.img, R.id.filename};

        /*SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.icon, c, from , to , 1
        );
        */
        GridView grid = (GridView) findViewById(R.id.grid);
        GridImageAdapter adapter = new GridImageAdapter( this, R.layout.icon, c, from , to , 1);
        grid.setAdapter(adapter);
    }

    class GridImageAdapter extends SimpleCursorAdapter{

        public GridImageAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                View v = getLayoutInflater().inflate(R.layout.icon, null);
                ImageView iv = (ImageView) v.findViewById(R.id.img);
                TextView tv = (TextView) v.findViewById(R.id.filename);
                Cursor c = getCursor();
                c.moveToPosition(position);
                tv.setText(c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                int id = c.getInt(c.getColumnIndex(MediaStore.Images.Media._ID));

                iv.setImageURI(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id+""));
                convertView = v;
            }
            return convertView;
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

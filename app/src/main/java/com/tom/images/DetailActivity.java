package com.tom.images;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private int pos;
    private Cursor cursor;
    private ImageView image;
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detector = new GestureDetector(this, this);

        image = (ImageView) findViewById(R.id.image);
        pos = getIntent().getIntExtra("POS", 0);
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        Log.d("POS", pos + "");
        cursor.moveToPosition(pos);

        updateImage();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    private void updateImage() {
        int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        image.setImageURI(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id + ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("GEST", "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("GEST", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("GEST", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("GEST", "onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("GEST", "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("GEST", "onFling");
        Log.d("EVT", "("+e1.getX()+","+e1.getY()+"),("+e2.getX()+","+e2.getY()+")");
        Log.d("VEL", velocityX+"/"+velocityY);
        float distance = e2.getX()-e1.getX();
        if (distance>100 && pos >0){ //to the right
            cursor.moveToPrevious();
            updateImage();
            pos--;
        }else if (distance<-100 && pos<cursor.getCount()-1){ // to the left
            cursor.moveToNext();
            updateImage();
            pos++;
        }

        return false;
    }
}

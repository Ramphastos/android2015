package com.tom.atm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final int REQUEST_LOGIN = 5;
    String[] funcs = {"餘額查詢","交易明細", "投資清單", "更改密碼","離開"};
    int[] icons = {R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4, R.drawable.f5};

    boolean logon = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!logon){
            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
            startActivityForResult(intent, REQUEST_LOGIN);
        }
        GridView grid = (GridView) findViewById(R.id.grid);
        IconAdapter adapter = new IconAdapter();
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);
//        ArrayAdapter adapter = new ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, funcs);
//        grid.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("POS", position+"");
        switch (position){
            case 0:
                startActivity(new Intent(this, BalanceActivity.class));
                break;
            case 1:
                break;
            case 2:
                break;

        }
    }

    class IconAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return funcs.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null) {
                View v = getLayoutInflater().inflate(R.layout.icon, null);
                ImageView iv = (ImageView) v.findViewById(R.id.icon_image);
                TextView tv = (TextView) v.findViewById(R.id.icon_text);
                tv.setText(funcs[position]);
                iv.setImageResource(icons[position]);
                convertView = v;
            }
            return convertView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_LOGIN){
            if (resultCode!=RESULT_OK){
                Toast.makeText(this, "抓包", Toast.LENGTH_LONG).show();
                finish();
            }
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

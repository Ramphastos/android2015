package com.tom.expense;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private DBHelper helper;
    private EditText edDate;
    private EditText edName;
    private EditText edAmount;
    private DatePicker dpicker;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        dpicker.updateDate(2011, 10, 10);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"早餐", "午餐"});
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        helper = new DBHelper(this, "expense.db", null, 1);
    }

    private void findViews() {
        edDate = (EditText) findViewById(R.id.udate);
        edName = (EditText) findViewById(R.id.name);
        edAmount = (EditText) findViewById(R.id.amount);
        dpicker = (DatePicker) findViewById(R.id.udate2);
        spinner = (Spinner) findViewById(R.id.spinner);
    }

    public void add(View v){

//        String udate = edDate.getText().toString();

        String udate = new StringBuffer()
                .append(dpicker.getYear())
                .append("-")
                .append(dpicker.getMonth()+1)
                .append("-")
                .append(dpicker.getDayOfMonth()).toString();

        String name = edName.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());
//        helper.getWritableDatabase().execSQL("insert into expense(udate,name,amount)" +
//                " values('" + udate+"','" +name+"'," + amount + ")");

        ContentValues values = new ContentValues();
        values.put("udate", udate);
        values.put("name", name);
        values.put("amount", amount);
        long id = helper.getWritableDatabase().insert("expense", null, values);
        Log.d("EXP", id+"");
    }

    public void query(View v){
        startActivity(new Intent(this, QueryActivity.class));
        /*
        Cursor c = helper.getReadableDatabase().query("expense", null, null, null, null, null, null);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("_id"));
            String udate = c.getString(c.getColumnIndex("udate"));
            String name = c.getString(c.getColumnIndex("name"));
            int amount = c.getInt(c.getColumnIndex("amount"));
            Log.d("ROW", id+"/"+udate+"/"+name+"/"+amount);
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

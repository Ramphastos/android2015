package com.tom.atm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        AtmApplication myapp = (AtmApplication)getApplication();
        String s = "http://j.snpy.org/atm/h?userid="+myapp.userid+"&pw="+myapp.passwd;
        new HistoryTask().execute(s);
    }

    class HistoryTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuffer sb = new StringBuffer();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                /*
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);*/
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = in.readLine();
                while(line!=null){
                    sb.append(line);
                    line = in.readLine();
                }
                Log.d("RESULT", sb.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                List<Map<String,String>> data = new ArrayList<>();
                JSONArray array = new JSONArray(s);
                for (int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    int amount = obj.getInt("amount");
                    String date = obj.getString("date");
                    String userid = obj.getString("userid");
                    Log.d("OBJ", amount+"/"+date+"/"+userid);
                    Map<String, String> row = new HashMap<>();
                    row.put("amount", amount+"");
                    row.put("date", date);
                    row.put("userid", userid);
                    data.add(row);
                }
                ListView list = (ListView) findViewById(R.id.list);
                String[] from = {"date", "amount", "userid"};
//                int[] to = {android.R.id.text1, android.R.id.text2};
                int[] to = {R.id.col_date, R.id.col_amount, R.id.col_userid};
                SimpleAdapter adapter = new SimpleAdapter(HistoryActivity.this,
                        data, R.layout.row,from, to );
                list.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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

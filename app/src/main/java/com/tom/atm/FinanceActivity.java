package com.tom.atm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class FinanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        String[] ids = {"2454", "2330", "2474", "3008", "2803", "2201", "2501"};
        new FinanceTask().execute(ids);
    }

    class FinanceTask extends AsyncTask<String, Void, String>{
        String urlString = "http://finance.google.com/finance/info?client=ig&q=TPE:";
        @Override
        protected String doInBackground(String... params) {
            for (int i=0; i<params.length; i++){
                urlString = urlString+params[i];
                if (i<params.length-1)
                    urlString = urlString + ",";
            }
            Log.d("URL", urlString);
            StringBuffer sb = new StringBuffer();
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                /*
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);*/
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = in.readLine();
                while (line != null) {
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
            s = s.substring(3);
            try {
                List<Map<String, String>> data = new ArrayList<>();
                JSONArray array = new JSONArray(s);
                for (int i=0; i<array.length();i++){
                    Map<String, String> row = new HashMap<>();
                    JSONObject obj = array.getJSONObject(i);
                    row.put("t",obj.getString("t"));
                    row.put("l_fix",obj.getString("l_fix"));
                    row.put("c_fix",obj.getString("c_fix"));
                    row.put("cp_fix",obj.getString("cp_fix"));
                    data.add(row);
                }
                ListView list = (ListView) findViewById(R.id.list);
                String[] from = {"t", "l_fix", "c_fix", "cp_fix"};
                int[] to = {R.id.col1,R.id.col2,R.id.col3,R.id.col4};
                SimpleAdapter adapter = new SimpleAdapter(FinanceActivity.this,
                        data, R.layout.row_finance, from, to);
                list.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finance, menu);
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

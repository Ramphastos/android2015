package com.tom.atm;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private boolean rememberUserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText userid = (EditText) findViewById(R.id.userid);
        String uid = getSharedPreferences("atm", MODE_PRIVATE)
                .getString("PREF_USERID", "");
        userid.setText(uid);
        rememberUserid = getSharedPreferences("atm", MODE_PRIVATE)
                .getBoolean("PREF_REMEMBER_USERID", false);
        CheckBox cb = (CheckBox) findViewById(R.id.cb_remember_userid);
        cb.setChecked(rememberUserid);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rememberUserid = isChecked;
                getSharedPreferences("atm", MODE_PRIVATE).edit()
                        .putBoolean("PREF_REMEMBER_USERID", isChecked)
                        .commit();
                if (!isChecked){
                    getSharedPreferences("atm", MODE_PRIVATE)
                            .edit()
                            .remove("PREF_USERID")
                            .commit();
                }
            }
        });
    }

    public void login(View v){
        EditText edUserid = (EditText) findViewById(R.id.userid);
        EditText edPasswd = (EditText) findViewById(R.id.passwd);
        String uid = edUserid.getText().toString();
        String pw = edPasswd.getText().toString();
        if (uid.equals("jack") && pw.equals("1234")){
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            setResult(RESULT_OK);
            if (rememberUserid) {
                getSharedPreferences("atm", MODE_PRIVATE)
                        .edit()
                        .putString("PREF_USERID", uid)
                        .commit();
            }
            finish();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("登入")
                    .setMessage("登入失敗")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

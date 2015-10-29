package com.tom.freshchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ValueEventListener {

    private TextView msg;
    private EditText input;
    private Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ((ChatApplication)getApplication()).is


        msg = (TextView) findViewById(R.id.msg);
        input = (EditText) findViewById(R.id.editText);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://blistering-inferno-4737.firebaseio.com/");
        ref.child("message").setValue("Hello");
        ref.child("message").addValueEventListener(this);
    }

    public void send(View v){
        String txt = input.getText().toString();
        ref.child("message").setValue(txt);
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

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        msg.setText(dataSnapshot.getValue().toString());
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}

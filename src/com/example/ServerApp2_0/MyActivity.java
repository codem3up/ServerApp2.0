package com.example.ServerApp2_0;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyActivity extends Activity {
  /**
   * Called when the activity is first created.
   */
EditText user;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    user = (EditText)findViewById(R.id.userText);
  }

  public void connect(View v){
    String u = user.getText().toString();
    if(!u.equals("")) {
      Intent i = new Intent(this, ChatActivity.class);
      i.putExtra("USER", u);
      startActivity(i);
    }
  }
}
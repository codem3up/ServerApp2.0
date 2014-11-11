package com.example.ServerApp2_0;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;

/**
 * Created by drew on 11/11/14.
 */
public class ChatActivity extends Activity {
  /**
   * Called when the activity is first created.
   */
  ChatClient client;
  String USERNAME;
  String sentence = "";
  String response;
  EditText mEdit;
  TextView textView, recieved;
  boolean isRunning = false;
  Socket socket;
  private static final String SERVER_IP = "10.0.0.7";
  private static final int SERVERPORT = 6789;
  BufferedReader in;
  BufferedWriter out;
  InputStreamReader reader;
  private ObjectInputStream sInput;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chat);
    //   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
    //         .permitAll().build();
    //StrictMode.setThreadPolicy(policy);
    USERNAME = getIntent().getStringExtra("USER");
    mEdit = (EditText) findViewById(R.id.editText);
    recieved = (TextView) findViewById(R.id.response);
    Context context = this.getBaseContext();

    client = new ChatClient("10.0.0.7", 5678, "Andrew");
    client.execute();
    String text = "Connection Established\n";
    client.sendToServer(text);
        /*try{
            client.run();
        } catch (IOException ex){
            //na
        }*/
  }
  public void sendData(View v) {
    String text = mEdit.getText().toString();
    if (!text.equals("")) {
      String sData = USERNAME + ": " + mEdit.getText().toString();
      client.sendToServer(sData);
    }
  }

  public class ChatClient extends AsyncTask<String, Void, String> {
    BufferedReader in;
    String HOSTNAME;
    int PORT;
    String USERNAME;
    Socket socket;
    String scan;
    BufferedWriter out;
    TextView textView;
    final ScrollView scrollview;

    public ChatClient(String HOSTNAME, int PORT, String user) {
        scrollview = ((ScrollView) findViewById(R.id.scrollview));
        recieved = (TextView) findViewById(R.id.response);
        this.HOSTNAME = HOSTNAME;
        this.PORT = PORT;
        this.USERNAME = user;

    }

    @Override
    protected String doInBackground(String... params) {
      try {
        if (socket == null) {
          Socket socket = new Socket(HOSTNAME, PORT);
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        while (true) {
          scan = in.readLine();
          if (scan != null) {
            break;
          }
          scan = null;

        }
      } catch (IOException ex) {

      }
      return null;
    }

    public void sendToServer(String s) {
      if (out != null) {
        try {
          out.write(s);
          out.write("\n");
          out.flush();
        } catch (IOException ex) {

        }
      }

    }

    @Override
    protected void onPostExecute(String s) {
      //Write server message to the text view

      recieved.append(scan + "\n");
      scrollview.post(new Runnable() {
        @Override
        public void run() {
          scrollview.fullScroll(ScrollView.FOCUS_DOWN);
        }
      });
      client = new ChatClient("10.0.0.7", 5678, "Andrew");
      client.execute();
    }
  }
}
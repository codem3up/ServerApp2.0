package com.example.ServerApp2_0;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    String sentence = "";
    String response;
    EditText mEdit;
    TextView textView, recieved;
    boolean isRunning = false;
    Socket socket;
    private static final String SERVER_IP = "10.0.0.7";
    private static final int SERVERPORT = 6789;
    PrintWriter out, in;
    InputStreamReader reader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mEdit = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.tv);
        recieved = (TextView) findViewById(R.id.response);
    }

    public void sendData(View v){
        sentence = mEdit.getText().toString();
        ClientTask clientTask = new ClientTask();
        clientTask.execute();
    }

    public class ClientTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void...arg0){
            Socket socket = null;
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(sentence);

                ByteArrayOutputStream byteArrayOutputStream =
                        new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();

    /*
     * notice:
     * inputStream.read() will block if no data return
     */
                while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            }finally{
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            recieved.setText(response);
            super.onPostExecute(result);
        }

    }
}



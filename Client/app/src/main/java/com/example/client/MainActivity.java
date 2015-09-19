package com.example.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends Activity {

    Button btn;
    EditText et,serverResponse;
    Socket client = null;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    String servresp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button1);
        et = (EditText) findViewById(R.id.editText);
        serverResponse = (EditText) findViewById(R.id.serverResponse);

        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                       String clientMessage = et.getText().toString();
                                       //creating connection to server
                                       ConnectToServer connectToServerTask = new ConnectToServer(clientMessage);
                                       connectToServerTask.execute();
                                       //set the editText with the response from the server
                                       serverResponse.setText(servresp);
                                   }
                               }
        );
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
    class ConnectToServer extends AsyncTask<Void, String, Void> {

        String clientMessage;

        public ConnectToServer(String clientMessage) {
        this.clientMessage = clientMessage;
        }


        @Override
        protected Void doInBackground(Void... unused) {

            try {
                //socket opening to server
                client = new Socket("192.168.1.131",4000);
                //Opening stream from client to server
                oos = new ObjectOutputStream(client.getOutputStream());
                oos.writeObject(clientMessage);
                oos.flush();

                //Opening stream from server to client
                ois = new ObjectInputStream(client.getInputStream());
                try {
                    servresp = ((String) ois.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return (null);
        }
    }


}

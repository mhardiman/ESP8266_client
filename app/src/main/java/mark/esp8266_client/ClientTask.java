package mark.esp8266_client;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by Mark on 6/26/2016.
 */
public class ClientTask extends AsyncTask <Integer, Void, Integer> {

    Context mainContext = null;
    public ClientTask(Context main){
        mainContext = main;
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        int result = -1;
        Socket socket = null;
        try {
            socket = new Socket("192.168.0.102", 5555);
            Log.d("general", "Socket opened.");
            //ByteArrayOutputStream byteOut = socket.getout
            OutputStream outstream = socket.getOutputStream();
            outstream.write(params[0]);
            result = params[0];
        } catch (IOException e) {
            Log.e("error", "Failed to initialize socket" + e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    //result = 2;
                    Log.d("general", "Socket closed.");
                } catch (IOException e) {
                    Log.e("error", "Failed to close socket");
                }
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        CharSequence text = "";

        switch (result) {
            case -1:
                text = "Could not connect to server.";
                break;
            case 0:
                text = "Closing...";
                //clientAct.state = 0;
                break;
            case 1:
                text = "Opening...";
                //clientAct.state = 1;
                break;
        }

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(mainContext, text, duration);
        toast.show();
    }
}
//ClientActivity clientAct = (ClientActivity)mainContext.getApplicationContext();
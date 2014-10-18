package com.example.androidspp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.androidspp.events.PublishResultEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Handler;

import de.greenrobot.event.EventBus;

/**
 * Created by ilya on 17.10.14.
 */
public class TruePulseReader {
    private BluetoothDevice device;
    private BluetoothSocket btSocket;
    private TextView output;
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ReadingDataTask readingDataTask;

    public TruePulseReader(BluetoothDevice device, TextView debugOutput) {
        this.device = device;
        output = debugOutput;
    }

    public void sendTestMessage() {
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            output.append("Fatal Error In onResume() and socket create failed: " + e.getMessage() + ".");
            e.printStackTrace();
            return;
        }
        output.append("BluetoothSocket created \n");

        try {
            btSocket.connect();
        } catch (IOException e) {
            output.append("Fatal Error In onResume() and socket connect failed: " + e.getMessage() + ".");
            e.printStackTrace();
            return;
        }
        output.append("BluetoothSocket connected \n");

        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(btSocket.getOutputStream());
        } catch (IOException e) {
            output.append("Fatal Error In onResume() output stream creation failed: " + e.getMessage() + ".");
            e.printStackTrace();
            return;
        }

        output.append("Output stream created \n");

        try {
            dos.writeChars("$DU,0\r\n");//distance units - meters
            dos.writeChars("$MM,0\r\n"); //MEASUREMENT MODE - horizontal distance
            dos.writeChars("$GO\r\n");
            dos.flush();
//            dos.close();
        } catch (Exception e) {
            output.append("Fatal Error In onResume() output stream write failed: " + e.getMessage() + ".");
            e.printStackTrace();
            return;
        }

        output.append("Message sent \n");

        readingDataTask = new ReadingDataTask(btSocket);
        readingDataTask.execute();
    }

    private class ReadingDataTask extends AsyncTask<Void, Void, Void> {
        private BluetoothSocket btSocket;
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;
        private String error = "";

        private ReadingDataTask(BluetoothSocket btSocket) {
            try {
                dataInputStream = new DataInputStream(btSocket.getInputStream());
                dataOutputStream = new DataOutputStream(btSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                while (true) {
                    String response = dataInputStream.readLine();
                    EventBus.getDefault().post(new PublishResultEvent("Response: " + response + "\n"));
                    Thread.sleep(500);
                    dataOutputStream.writeChars("$GO\r\n");
                    dataOutputStream.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
                error = "\n" + "Error in ReadingDataTask: " + e.getMessage() + "\n";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(!TextUtils.isEmpty(error)){
                output.append(error);
            }
        }
    }
}

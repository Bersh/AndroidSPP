package com.example.androidspp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.view.View;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by ilya on 17.10.14.
 */
public class TruePulseReader {
    private BluetoothDevice device;
    private BluetoothSocket btSocket;
    private TextView output;
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

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

        String message = "$PLTIT,HV\r\n";
        try {
            dos.writeChars(message);
            dos.flush();
            dos.close();
        } catch (Exception e) {
            output.append("Fatal Error In onResume() output stream write failed: " + e.getMessage() + ".");
            e.printStackTrace();
            return;
        }


        output.append("Message sent \n");

        DataInputStream dataInputStream;
        try {
            dataInputStream = new DataInputStream(btSocket.getInputStream());
            while (true) {
                String response = dataInputStream.readLine();
                output.append("Response: " + response);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            output.append("Fatal Error In onResume() input stream read failed: " + e.getMessage() + ".");
            e.printStackTrace();
            return;
        }
    }
}

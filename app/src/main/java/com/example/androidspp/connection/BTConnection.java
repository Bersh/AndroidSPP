package com.example.androidspp.connection;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.example.androidspp.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import timber.log.Timber;

public class BTConnection implements IConnection {
    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket = null;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private IRawDataListener listener;
    private Thread readingDataThread;

    public BTConnection(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override
    public boolean connect() {
        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(Constants.MY_UUID);
            bluetoothSocket.connect();
            dataOutputStream = new DataOutputStream(bluetoothSocket.getOutputStream());
            dataInputStream = new DataInputStream(bluetoothSocket.getInputStream());
        } catch (IOException e) {
            Timber.e("Fatal Error in connect() socket create failed: " + e.getMessage() + ".");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean disconnect() {
        try {
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
            }
        } catch (IOException e) {
            Timber.e("Fatal Error in disconnect() socket close failed: " + e.getMessage() + ".");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void sendCommand(byte[] command) {
        if (!checkSocket()) {
            return;
        }

        try {
            dataOutputStream.write(command);
            dataOutputStream.flush();
        } catch (IOException e) {
            Timber.e("Can't send command: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void sendCommand(String command) {
        if (!checkSocket()) {
            return;
        }

        try {
            dataOutputStream.writeChars(command);
            dataOutputStream.flush();
        } catch (IOException e) {
            Timber.e("Can't send command: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String readString() {
        String result = null;
        try {
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            Timber.e("Can't read string: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void read(byte[] buffer) {
        try {
            dataInputStream.read(buffer);
        } catch (IOException e) {
            Timber.e("Can't read bytes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setListener(IRawDataListener listener) {
        if (dataInputStream != null) {
            this.listener = listener;
            readingDataThread = new Thread(new ReadingRunnable(dataInputStream));
            readingDataThread.start();
        } else {
            Timber.e("Can't set listener, no input stream");
        }
    }

    private boolean checkSocket() {
        if (bluetoothSocket != null) {
            return true;
        } else {
            Timber.e("Can't perform operation. No active socket found.");
            return false;
        }
    }

    private class ReadingRunnable implements Runnable {
        private DataInputStream dataInputStream;

        private ReadingRunnable(DataInputStream dataInputStream) {
            this.dataInputStream = dataInputStream;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes = 0;
            while (true) {
                try {
                    byte[] tmpBuffer = new byte[1024];
                    int tmpBytes = dataInputStream.read(tmpBuffer);
                    System.arraycopy(tmpBuffer, 0, buffer, bytes, tmpBytes);
                    bytes += tmpBytes;
                    byte lastByte = buffer[buffer.length - 1];
                    if(((char)lastByte) != '\n') {
                        continue;
                    }
                    listener.onAcceptData(buffer, bytes);
                } catch (IOException e) {
//                    Timber.e("Data read failed: " + e.getMessage()); todo need to run in UI thread
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}

package com.example.androidspp.connection;


import android.bluetooth.BluetoothDevice;

public interface IConnection {
    public boolean connect();
    public boolean disconnect();
    public void sendCommand(byte[] command);
    public void sendCommand(String command);
    public String readString();
    public void read(byte[] buffer);
}

package com.example.androidspp;

public interface IConnection {
    void Connect();

    void Disconnect();

    void SendCommand(byte[] data);

    void set_Listener(IRawDataListener listener);
}

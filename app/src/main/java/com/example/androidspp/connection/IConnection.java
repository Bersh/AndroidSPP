package com.example.androidspp.connection;

public interface IConnection {
    public boolean connect();

    public boolean disconnect();

    public void sendCommand(byte[] command);

    public void sendCommand(String command);

    public String readString();

    /**
     * Sync data read
     *
     * @param buffer target buffer
     */
    public void read(byte[] buffer);

    /**
     * Listener will be notified once there is new data from socket
     *
     * @param listener listener
     */
    public void setListener(IRawDataListener listener);
}

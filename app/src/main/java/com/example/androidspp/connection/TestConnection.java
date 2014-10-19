package com.example.androidspp.connection;

import com.example.androidspp.connection.IConnection;
import com.example.androidspp.connection.IRawDataListener;

import java.util.Timer;
import java.util.TimerTask;

public class TestConnection implements IConnection {

    IRawDataListener listener;
    Timer timer;
    int mode;
    final int delayInMs = 5000;
    final String[] messageTemplates = new String[]{"123", "456", "789", "-888"};
    int idx = 0;

    @Override
    public boolean connect() {
        // TODO Auto-generated method stub
        // this will run when timer elapses
        TimerTask myTimerTask = new TimerTask() {

            @Override
            public void run() {
                if (listener != null) {
                    byte[] data = messageTemplates[idx++ % messageTemplates.length].getBytes();
                    listener.onAcceptData(data, data.length);
                }
            }

        };
        // new timer
        timer = new Timer();

        // schedule timer
        timer.schedule(myTimerTask, 0, delayInMs);
        return true;
    }

    @Override
    public boolean disconnect() {
        // TODO Auto-generated method stub
        if (timer != null)
            timer.cancel();
        timer.purge();
        timer = null;
        return true;
    }

    @Override
    public void sendCommand(byte[] data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendCommand(String command) {

    }

    @Override
    public String readString() {
        return null;
    }

    @Override
    public void read(byte[] buffer) {

    }

    @Override
    public void setListener(IRawDataListener listener) {
        this.listener = listener;
    }

    public void set_Mode(int mode) {
        this.mode = mode;
    }

}

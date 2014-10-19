package com.example.androidspp;

//com.example.androidspp

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
    public void Connect() {
        // TODO Auto-generated method stub
        // this will run when timer elapses
        TimerTask myTimerTask = new TimerTask() {

            @Override
            public void run() {
                if (listener != null) {
                    byte[] data = messageTemplates[idx++ % messageTemplates.length].getBytes();
                    listener.OnAcceptData(data, data.length);
                }
            }

        };
        // new timer
        timer = new Timer();

        // schedule timer
        timer.schedule(myTimerTask, 0, delayInMs);

    }

    @Override
    public void Disconnect() {
        // TODO Auto-generated method stub
        if (timer != null)
            timer.cancel();
        timer.purge();
        timer = null;
    }

    @Override
    public void SendCommand(byte[] data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void set_Listener(IRawDataListener listener) {
        // TODO Auto-generated method stub
        this.listener = listener;
    }

    public void set_Mode(int mode) {
        this.mode = mode;
    }

}

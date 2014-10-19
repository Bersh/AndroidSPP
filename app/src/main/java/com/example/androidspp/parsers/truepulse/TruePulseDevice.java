package com.example.androidspp.parsers.truepulse;

import com.example.androidspp.IDevice;
import com.example.androidspp.command.ICommandBuilder;
import com.example.androidspp.connection.IConnection;

public class TruePulseDevice implements IDevice {

    @Override
    public ICommandBuilder GetCommandBuilder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int GetDeviceMode() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void SetupDevice(IConnection connection) {
        // TODO Auto-generated method stub
    }

    @Override
    public String getName() {
        return "TruePulse";
    }

}

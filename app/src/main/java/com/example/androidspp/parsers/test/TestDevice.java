package com.example.androidspp.parsers.test;

import com.example.androidspp.IDevice;
import com.example.androidspp.command.ICommandBuilder;
import com.example.androidspp.connection.IConnection;

public class TestDevice implements IDevice {

    @Override
    public ICommandBuilder GetCommandBuilder() {
        return new com.example.androidspp.parsers.test.TestCommandBuilder();
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
        // TODO Auto-generated method stub
        return "Test device";
    }

}

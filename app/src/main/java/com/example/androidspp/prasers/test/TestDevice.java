package com.example.androidspp.parsers.test;

import com.example.androidspp.ICommandBuilder;
import com.example.androidspp.IConnection;
import com.example.androidspp.IDevice;

public class TestDevice implements IDevice {

    @Override
    public ICommandBuilder GetCommandBuilder() {
        // TODO Auto-generated method stub
        return new TestCommandBuilder();
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

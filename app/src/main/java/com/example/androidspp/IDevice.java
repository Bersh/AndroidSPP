package com.example.androidspp;

import com.example.androidspp.command.ICommandBuilder;
import com.example.androidspp.connection.IConnection;

public interface IDevice {
    ICommandBuilder GetCommandBuilder();

    int GetDeviceMode();

    void SetupDevice(IConnection connection);

    String getName();
}

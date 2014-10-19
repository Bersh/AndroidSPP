package com.example.androidspp;

public interface IDevice {
    ICommandBuilder GetCommandBuilder();

    int GetDeviceMode();

    void SetupDevice(IConnection connection);

    String getName();
}

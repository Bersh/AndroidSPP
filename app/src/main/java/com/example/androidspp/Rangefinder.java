package com.example.androidspp;

import java.util.Date;


public class Rangefinder implements IRawDataListener {
    IConnection connection;
    IDevice device;
    IRangefinderListener client;
    ICommandBuilder commBuilder;
    RegisterCommand[] commandsBuffer = new RegisterCommand[2];

    public void setConnection(IConnection connection) {
        this.connection = connection;
        if (connection != null)
            connection.set_Listener(this);
    }

    public IConnection getConnection() {
        return this.connection;
    }

    public void setDevice(IDevice device) {
        this.device = device;
        if (device != null) {
            this.commBuilder = device.GetCommandBuilder();
        } else {
            this.commBuilder = null;
        }
    }

    public IDevice getDevice() {
        return this.device;
    }

    public void setClient(IRangefinderListener listener) {
        this.client = listener;
    }

    public void Start() {
        connection.Connect();
        device.SetupDevice(connection);
    }

    public void Stop() {
        connection.Disconnect();
    }

    public boolean IsReady() {
        return this.connection != null && this.device != null;
    }

    @Override
    public void OnAcceptData(byte[] data, int bytes) {
        // TODO Auto-generated method stub
        byte[] ndata = new byte[bytes];
        System.arraycopy(data, 0, ndata, 0, bytes);
        ICommand command = commBuilder.BuildCommand(data);
        if (command != null) {
            if (command instanceof OnePointCommand) {
                PutCommandInBuffer((OnePointCommand) command);
            }
            if (command instanceof ITwoPointCommand) {
                EnreachCommand((TwoPointCommand) command);
            }

            client.OnCommand(command);
        }
    }

    void PutCommandInBuffer(IOnePointCommand c) {
        commandsBuffer[1] = commandsBuffer[0];
        commandsBuffer[0] = new RegisterCommand();
        commandsBuffer[0].Command = c;
        commandsBuffer[0].RegisteredMoment = new Date().getTime();
    }

    void EnreachCommand(TwoPointCommand command) {
        if (commandsBuffer[0] == null || commandsBuffer[1] == null) return;
        if (command.getFirstPoint() == null)
            command.setFirstPoint(commandsBuffer[1].Command);
        if (command.getSecondPoint() == null)
            command.setSecondPoint(commandsBuffer[0].Command);
        if (command.getTime() == 0)
            command.setTime(commandsBuffer[1].RegisteredMoment - commandsBuffer[0].RegisteredMoment);
    }
}
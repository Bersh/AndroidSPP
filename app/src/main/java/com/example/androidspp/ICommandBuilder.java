package com.example.androidspp;

public interface ICommandBuilder {
    ICommand BuildCommand(byte[] rawCommands);
}

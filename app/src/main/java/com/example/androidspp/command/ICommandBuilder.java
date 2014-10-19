package com.example.androidspp.command;

public interface ICommandBuilder {
    ICommand BuildCommand(byte[] rawCommands);
}

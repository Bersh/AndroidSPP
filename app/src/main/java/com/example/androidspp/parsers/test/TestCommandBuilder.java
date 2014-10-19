package com.example.androidspp.parsers.test;

import com.example.androidspp.command.ICommand;
import com.example.androidspp.command.ICommandBuilder;
import com.example.androidspp.command.OnePointCommand;
import com.example.androidspp.command.TwoPointCommand;

public class TestCommandBuilder implements ICommandBuilder {

    @Override
    public ICommand BuildCommand(byte[] rawCommands) {
        // TODO Auto-generated method stub
        String str = new String(rawCommands);

        int a = Integer.parseInt(str);
        if (a > 0) {
            OnePointCommand c = new OnePointCommand();
            c.setAzimuth(a % 10);
            c.setCommonDistance(a);
            c.setHorizontalDistance(a * 2);
            c.setInclination(a % 100);
            return c;
        } else {

            TwoPointCommand c2 = new TwoPointCommand();
            c2.setDistance(-a);
            c2.setAzimuth(-a % 10);
            return c2;
        }

    }

}

package com.example.androidspp.parsers.truepulse;


import java.io.UnsupportedEncodingException;

public class TruePulseMLCommand extends TruePulseHVCommand {

    private TruePulseHVCommand shot1, shot2;

    public TruePulseMLCommand(byte[][] data) throws UnsupportedEncodingException {
        super(new byte[][]{data[2]});
        this.shot1 = new TruePulseHVCommand(new byte[][]{data[0]});
        this.shot2 = new TruePulseHVCommand(new byte[][]{data[1]});
    }

    public TruePulseHVCommand getShotOne() {
        return shot1;
    }

    public TruePulseHVCommand getShotTwo() {
        return shot2;
    }

}

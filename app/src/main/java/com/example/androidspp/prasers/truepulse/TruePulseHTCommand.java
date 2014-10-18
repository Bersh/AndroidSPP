package com.example.androidspp.prasers.truepulse;

import java.io.UnsupportedEncodingException;

public class TruePulseHTCommand extends TruePulseHVCommand{

	public TruePulseHTCommand(byte[][] data) throws UnsupportedEncodingException {
		super(data);	
	}
	
	public double getHeight(){
		return horizontalDistance;
	}

}

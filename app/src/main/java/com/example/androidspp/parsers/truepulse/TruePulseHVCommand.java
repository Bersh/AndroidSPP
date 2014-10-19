package com.example.androidspp.parsers.truepulse;

import android.text.TextUtils;

import com.example.androidspp.command.OnePointCommand;

import java.io.UnsupportedEncodingException;

public class TruePulseHVCommand extends OnePointCommand {


    public static final String test_message = "18.00,F,185.20,D,6.90,D,18.00,F*66";

    private static final int HORIZONTAL_DISTANCE_INDEX = 0;
    private static final int AZIMUTH_INDEX = 1;
    private static final int INCLINATION_INDEX = 2;
    private static final int COMMON_DISTANCE_INDEX = 3;

    private int csum;

    private String message;

    public TruePulseHVCommand(byte[][] data) throws UnsupportedEncodingException {
        message = Utils.getTruePulseMessageBody(new String(data[0], "UTF-8"));
        calculateCheckSum();
        String[] array = message.split("\\" + TruePulseConstants.ITEMS_DIVIDER);
        for (int i = 0; i < array.length; i += 2)
            if (!TextUtils.isEmpty(array[i]))
                switch (i) {
                    case HORIZONTAL_DISTANCE_INDEX:
                        horizontalDistance = calculateDistance(array[i], array[i + 1]);
                        break;
                    case AZIMUTH_INDEX:
                        azimuth = Double.valueOf(array[i]);
                        break;
                    case INCLINATION_INDEX:
                        inclination = Double.valueOf(array[i]);
                        break;
                    case COMMON_DISTANCE_INDEX:
                        distance = calculateDistance(array[i], array[i + 1]);
                        break;
                }
    }

    private double calculateDistance(String value, String label) {
        double distance = -1;
        if (label.equals(MetricsConstants.METER_LABEL))
            distance = Double.valueOf(value);
        else if (label.equals(MetricsConstants.FOOT_LABEL))
            distance = Double.valueOf(value) * MetricsConstants.FOOT;
        else if (label.equals(MetricsConstants.YARD_LABEL))
            distance = Double.valueOf(value) * MetricsConstants.YARD;
        return distance;
    }

    private void calculateCheckSum() {
        int index = message.lastIndexOf(TruePulseConstants.CSUM_DIVIDER);
        String hex = message.substring(index + 1);
        message = message.substring(0, index);
        csum = Utils.parseHex(hex);
    }

    public int getCheckSum() {
        return csum;
    }

    @Override
    public double getHorizontalDistance() {
        return horizontalDistance;
    }

    @Override
    public double getAzimuth() {
        return azimuth;
    }

    @Override
    public double getInclination() {
        return inclination;
    }

    @Override
    public double getCommonDistance() {
        return distance;
    }
}

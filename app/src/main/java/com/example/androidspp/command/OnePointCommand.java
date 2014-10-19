package com.example.androidspp.command;

import com.example.androidspp.BaseCommand;

public class OnePointCommand extends BaseCommand implements IOnePointCommand {

    protected double horizontalDistance;
    protected double azimuth;
    protected double inclination;
    protected double distance;

    /* (non-Javadoc)
     * @see com.example.androidspp.IOnePointCommand1#getHorizontalDistance()
     */
    @Override
    public double getHorizontalDistance() {
        return horizontalDistance;
    }

    ;

    public void setHorizontalDistance(double value) {
        horizontalDistance = value;
    }

    /* (non-Javadoc)
     * @see com.example.androidspp.IOnePointCommand1#getAzimuth()
     */
    @Override
    public double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(double value) {
        azimuth = value;
    }

    /* (non-Javadoc)
     * @see com.example.androidspp.IOnePointCommand1#getInclination()
     */
    @Override
    public double getInclination() {
        return inclination;
    }

    public void setInclination(double value) {
        inclination = value;
    }

    /* (non-Javadoc)
     * @see com.example.androidspp.IOnePointCommand1#getCommonDistance()
     */
    @Override
    public double getCommonDistance() {
        return distance;
    }

    public void setCommonDistance(double value) {
        distance = value;
    }
}



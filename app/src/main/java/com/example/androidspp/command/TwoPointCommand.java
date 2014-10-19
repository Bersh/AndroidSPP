package com.example.androidspp.command;


import com.example.androidspp.BaseCommand;

public class TwoPointCommand extends BaseCommand implements ITwoPointCommand {

    protected IOnePointCommand firstPoint;
    protected IOnePointCommand secondPoint;
    protected double distance;
    protected double time;
    protected double azimuth;

    /* (non-Javadoc)
    * @see com.example.androidspp.command.ITwoPointCommand#getFirstPoint()
    */
    @Override
    public IOnePointCommand getFirstPoint() {
        return firstPoint;
    }

    /* (non-Javadoc)
    * @see com.example.androidspp.command.ITwoPointCommand#getSecondPoint()
    */
    @Override
    public IOnePointCommand getSecondPoint() {
        return secondPoint;
    }

    /* (non-Javadoc)
    * @see com.example.androidspp.command.ITwoPointCommand#getAzimuth()
    */
    @Override
    public double getAzimuth() {
        return azimuth;
    }

    /* (non-Javadoc)
    * @see com.example.androidspp.command.ITwoPointCommand#getTime()
    */
    @Override
    public double getTime() {
        return time;
    }

    /* (non-Javadoc)
    * @see com.example.androidspp.command.ITwoPointCommand#getDistance()
    */
    @Override
    public double getDistance() {
        return distance;
    }

    public void setFirstPoint(IOnePointCommand value) {
        firstPoint = value;
    }

    public void setSecondPoint(IOnePointCommand value) {
        secondPoint = value;
    }

    public void setAzimuth(double value) {
        azimuth = value;
    }

    public void setTime(double value) {
        time = value;
    }

    public void setDistance(double value) {
        distance = value;
    }
}
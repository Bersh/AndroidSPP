package com.example.androidspp.prasers.truepulse;

public abstract class OnePointCommand {

    protected double horizontalDistance;
    protected double azimuth;
    protected double inclination;
    protected double commonDistance;

    abstract double getHorizontalDistance();

    abstract double getAzimuth();

    abstract double getInclination();

    abstract double getCommonDistance();

}

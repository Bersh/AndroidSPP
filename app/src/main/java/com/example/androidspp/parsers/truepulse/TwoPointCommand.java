package com.example.androidspp.parsers.truepulse;

public abstract class TwoPointCommand {

    protected double horizontalDistance;
    protected double azimuth;
    protected double inclination;
    protected double commonDistance;

    abstract double getHorizontalDistance();

    abstract double getAzimuth();

    abstract double getInclination();

    abstract double getCommonDistance();

}

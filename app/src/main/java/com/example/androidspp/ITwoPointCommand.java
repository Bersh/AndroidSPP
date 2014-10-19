package com.example.androidspp;

public interface ITwoPointCommand extends ICommand {

    public abstract IOnePointCommand getFirstPoint();

    public abstract IOnePointCommand getSecondPoint();

    public abstract double getAzimuth();

    public abstract double getTime();

    public abstract double getDistance();

}
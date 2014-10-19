package com.example.androidspp;

public interface IOnePointCommand extends ICommand {

    public abstract double getHorizontalDistance();

    public abstract double getAzimuth();

    public abstract double getInclination();

    public abstract double getCommonDistance();

}
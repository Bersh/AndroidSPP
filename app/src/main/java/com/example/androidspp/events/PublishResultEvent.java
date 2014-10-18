package com.example.androidspp.events;

public class PublishResultEvent {
    private String result;

    public PublishResultEvent(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}

package com.example.androidspp;


import android.app.Application;
import android.widget.TextView;

import com.splunk.mint.Mint;

import timber.log.Timber;

public class AndroidSppApplication extends Application {
    private TextView debugOutput;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new LogToTextViewTree());
        Mint.initAndStartSession(this, "82b0ad83");
    }

    public void setDebugOutput(TextView debugOutput) {
        this.debugOutput = debugOutput;
    }

    private class LogToTextViewTree extends Timber.DebugTree {

        @Override
        public void e(String message, Object... args) {
//            if (debugOutput != null) {
//                debugOutput.append(message);
//                debugOutput.append("\n");
//            }
            super.e("ERROR: " + message, args);
        }
    }

}

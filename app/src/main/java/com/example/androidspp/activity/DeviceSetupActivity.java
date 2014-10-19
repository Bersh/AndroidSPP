package com.example.androidspp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.androidspp.Globals;
import com.example.androidspp.IDevice;
import com.example.androidspp.R;
import com.example.androidspp.parsers.test.TestDevice;
import com.example.androidspp.parsers.truepulse.TruePulseDevice;

public class DeviceSetupActivity extends ActionBarActivity {

    IDevice[] devices;
    RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setup);
/*
        if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
        devices = new IDevice[3];
        devices[0] = null;
        devices[1] = new TruePulseDevice();
        devices[2] = new TestDevice();
        group = (RadioGroup) findViewById(R.id.radioGroupSetectDevice);
        RadioButton button;

        int selectedId = R.id.radioButtonNoDevice;
        String selectedDeviceName = "";
        if (Globals.Rangefinder.getDevice() != null)
            selectedDeviceName = Globals.Rangefinder.getDevice().getName();
        for (int i = 1; i < devices.length; i++) {
            button = new RadioButton(this);
            button.setText(devices[i].getName());
            group.addView(button);

            if (devices[i].getName().equals(selectedDeviceName))
                selectedId = button.getId();
        }
        group.check(selectedId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.device_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void Accept(View view) {

        int radioButtonID = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(radioButtonID);
        int idx = group.indexOfChild(radioButton);
        Globals.Rangefinder.setDevice(devices[idx]);
        finish();
    }


}

package com.example.androidspp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidspp.Globals;
import com.example.androidspp.ICommand;
import com.example.androidspp.IOnePointCommand;
import com.example.androidspp.IRangefinderListener;
import com.example.androidspp.ITwoPointCommand;
import com.example.androidspp.R;
import com.example.androidspp.Rangefinder;

public class MainActivity extends ActionBarActivity implements IRangefinderListener {

    CheckBox cbAcceptingData;
    Rangefinder Rangefinder;
    TextView txtDistance;
    TextView txtAzimuth;
    TextView txtInclination;

    TextView txtDistance2;
    TextView txtAzimuth2;
    TextView txtTime;

    final Handler uiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cbAcceptingData = (CheckBox) findViewById(R.id.checkBoxAcceptingData);
        txtDistance = (TextView) findViewById(R.id.textDistance);
        txtAzimuth = (TextView) findViewById(R.id.textAzimuth);
        txtInclination = (TextView) findViewById(R.id.textInclination);

        txtDistance2 = (TextView) findViewById(R.id.textDistance2);
        txtAzimuth2 = (TextView) findViewById(R.id.textAzimuth2);
        txtTime = (TextView) findViewById(R.id.textTime);

        this.Rangefinder = new Rangefinder();
        this.Rangefinder.setClient(this);
        Globals.Rangefinder = this.Rangefinder;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


    public void ShowBTSettings(View view) {
        Intent intent = new Intent(this, BTSetupActivity.class);
        startActivity(intent);
    }

    public void ShowRgSettings(View view) {
        Intent intent = new Intent(this, DeviceSetupActivity.class);
        startActivity(intent);
    }

    public void AcceptingData(View view) {
        if (Rangefinder.IsReady()) {
            if (cbAcceptingData.isChecked())
                Rangefinder.Start();
            else Rangefinder.Stop();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "�� ������ ��� ���������� ��� �� ��������� Bluetooth", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void OnCommand(ICommand command) {
        if (command instanceof IOnePointCommand) ProcessOnePointCommand((IOnePointCommand) command);
        if (command instanceof ITwoPointCommand) ProcessTwoPointCommand((ITwoPointCommand) command);
    }

    void ProcessOnePointCommand(IOnePointCommand command) {
        final IOnePointCommand command1 = (IOnePointCommand) command;
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                if (command1 != null) {
                    txtDistance.setText(String.valueOf(command1.getCommonDistance()));
                    txtAzimuth.setText(String.valueOf(command1.getAzimuth()));
                    txtInclination.setText(String.valueOf(command1.getInclination()));
                }
            }
        });
    }

    void ProcessTwoPointCommand(ITwoPointCommand command) {
        final ITwoPointCommand command1 = (ITwoPointCommand) command;
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                if (command1 != null) {
                    txtDistance2.setText(String.valueOf(command1.getDistance()));
                    txtAzimuth2.setText(String.valueOf(command1.getAzimuth()));
                    txtTime.setText(String.valueOf(command1.getTime()));
                }
            }
        });
    }


}

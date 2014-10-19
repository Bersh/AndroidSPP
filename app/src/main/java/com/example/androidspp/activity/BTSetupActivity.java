package com.example.androidspp.activity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.example.androidspp.Globals;
import com.example.androidspp.IConnection;
import com.example.androidspp.R;
import com.example.androidspp.TestConnection;

public class BTSetupActivity extends ActionBarActivity {

    private BluetoothAdapter mBluetoothAdapter;

    CheckBox cbUseTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btsetup);


        //FrameLayout container = (FrameLayout)findViewById(R.id.container);
        cbUseTest = (CheckBox) findViewById(R.id.checkBoxBTSetupUseTest);
        if (Globals.Rangefinder.getConnection() instanceof TestConnection) {
            cbUseTest.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.btsetup, menu);
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


    private boolean checkBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null;
    }


    public void Accept(View view) {
        if (cbUseTest.isChecked()) {
            Globals.Rangefinder.setConnection(new TestConnection());
        } else

        {
            Globals.Rangefinder.setConnection(CreateBluetoothConnection());
        }

        finish();
    }

    IConnection CreateBluetoothConnection() {
        return null;
    }


}

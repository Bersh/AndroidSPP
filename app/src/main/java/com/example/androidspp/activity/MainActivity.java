package com.example.androidspp.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.androidspp.AndroidSppApplication;
import com.example.androidspp.DevicesAdapter;
import com.example.androidspp.R;
import com.example.androidspp.connection.BTConnection;
import com.example.androidspp.connection.IConnection;
import com.example.androidspp.connection.IRawDataListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;


public class MainActivity extends ActionBarActivity {
    private TextView output;
    private BluetoothAdapter mBluetoothAdapter;

    private DevicesAdapter adapter;
    private ProgressBar progressBar;
    private ListView foundDevices;
    private IConnection btConnection;
    private View commandsLayout;
    private TextView txtMode;
    private Spinner rangeFinderType;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            String mode = result.substring(7, 9);
            txtMode.setText("Mode : " + mode);
            output.append(result);
        }
    };

    private List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();

    private boolean registered = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = (TextView) findViewById(R.id.output);
        commandsLayout = findViewById(R.id.commands);
        txtMode = (TextView) findViewById(R.id.txt_mode);
        rangeFinderType = (Spinner) findViewById(R.id.rangefinder_type);
        ArrayList<String> rangefinderTypes = new ArrayList<String>();
        rangefinderTypes.add("TruePulse");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, rangefinderTypes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Button getDistanceButton = (Button) findViewById(R.id.btn_get_distance);
        rangeFinderType.setAdapter(dataAdapter);
        
        getDistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btConnection.sendCommand("$PLTIT,GO\r\n");
                btConnection.sendCommand("$PLTIT,ST\r\n");
            }
        });

        findViewById(R.id.btn_clear_output).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (output.getEditableText() != null) {
                    output.getEditableText().clear();
                }
            }
        });

        ((AndroidSppApplication) getApplication()).setDebugOutput(output);

        if (checkBT()) {
            initUI();
            createReceiver();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registered) {
            unregisterReceiver(mReceiver);
        }
    }

    private boolean checkBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null;
    }

    private void initUI() {
        ToggleButton toggleState = (ToggleButton) findViewById(R.id.toggleState);
        final Button startSearching = (Button) findViewById(R.id.startSearching);
        foundDevices = (ListView) findViewById(R.id.foundDevices);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        toggleState.setEnabled(true);
        toggleState.setChecked(mBluetoothAdapter.isEnabled());
        toggleState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setBluetoothEnabled(isChecked);
                startSearching.setEnabled(isChecked);
                if (!isChecked) {
                    devices.clear();
                    adapter.updateContent(devices);
                }
            }
        });

        startSearching.setEnabled(toggleState.isChecked());
        startSearching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devices.clear();
                adapter.updateContent(devices);
                startSearching();
            }
        });

        adapter = new DevicesAdapter(this, devices);
        foundDevices.setAdapter(adapter);
        foundDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                connectToDevice(position);
            }
        });
    }

    private void setBluetoothEnabled(boolean enabled) {
        if (enabled) {
            mBluetoothAdapter.enable();
        } else {
            mBluetoothAdapter.disable();
        }
    }

    private void startSearching() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
        if (output.getEditableText() != null) {
            output.getEditableText().clear();
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    // Create a BroadcastReceiver
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    if (!devices.contains(device)) {
                        devices.add(device);
                        adapter.updateContent(devices);
                    }
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    progressBar.setVisibility(View.GONE);
                    break;
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    Timber.d("Connected");
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    Timber.d("Disconnected");
                    break;
            }
        }
    };

    private void createReceiver() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, filter);
        registered = true;
    }

    private void connectToDevice(int position) {
        mBluetoothAdapter.cancelDiscovery();
        progressBar.setVisibility(View.GONE);
        foundDevices.setVisibility(View.GONE);
        BluetoothDevice device = devices.get(position);
        btConnection = new BTConnection(device);
        if (btConnection.connect()) {
            commandsLayout.setVisibility(View.VISIBLE);
            btConnection.setListener(new TestListener());
            output.append("Connected to target device");
        } else {
            output.append("Connect failed. Try again");
        }
    }

    private class TestListener implements IRawDataListener {

        @Override
        public void onAcceptData(byte[] data, int bytes) {
            String result = new String(Arrays.copyOf(data, bytes));
            handler.obtainMessage(1, result).sendToTarget();
        }
    }
}

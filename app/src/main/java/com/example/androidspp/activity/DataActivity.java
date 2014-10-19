package com.example.androidspp.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidspp.AndroidSppApplication;
import com.example.androidspp.Constants;
import com.example.androidspp.R;
import com.example.androidspp.connection.BTConnection;
import com.example.androidspp.connection.IConnection;
import com.example.androidspp.connection.IRawDataListener;

import java.util.Arrays;

public class DataActivity extends ActionBarActivity {

    private final int connect_rq_code = 1;
    private IConnection connection;
    private TextView output;
    private TextView txtMode;
    private Button btnGetDistance;
    private Button btnSetModeHV;
    private Button btnSetModeML;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result.length() > 10) {
                String mode = result.substring(7, 9);
                txtMode.setText("Mode : " + mode);
            }
            output.append(result);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        output = (TextView) findViewById(R.id.output);
        ((AndroidSppApplication) getApplication()).setDebugOutput(output);

        btnGetDistance = (Button) findViewById(R.id.btn_get_distance);
        txtMode = (TextView) findViewById(R.id.txt_mode);
        btnSetModeHV = (Button) findViewById(R.id.btn_set_mode_hv);
        btnSetModeML = (Button) findViewById(R.id.btn_set_mode_ml);

        btnSetModeML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection != null) {
                    connection.sendCommand("$PLTIT,RQ,MM,6\r\n");
                }
            }
        });

        btnSetModeHV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection != null) {
                    connection.sendCommand("$PLTIT,RQ,MM,0\r\n");
                }
            }
        });

        btnGetDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection != null) {
                    connection.sendCommand("$PLTIT,RQ,GO\r\n");
                    connection.sendCommand("$PLTIT,RQ,ST\r\n");
                }
            }
        });

        findViewById(R.id.btn_clear_output).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearOutput();
            }
        });

        findViewById(R.id.btn_connect_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataActivity.this, BtParamsActivity.class);
                startActivityForResult(intent, connect_rq_code);
            }
        });
    }

    private void setCommandsEnabled(boolean enabled) {
        btnSetModeHV.setEnabled(enabled);
        btnSetModeML.setEnabled(enabled);
        btnGetDistance.setEnabled(enabled);
    }

    @Override
    protected void onDestroy() {
        if (connection != null) {
            connection.disconnect();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        if (connect_rq_code == requestCode && data != null && resultCode == RESULT_OK) {
            BluetoothDevice bluetoothDevice = data.getParcelableExtra(Constants.EXTRA.EXTRA_KEY_BT_DEVICE);
            if (bluetoothDevice != null) {
                connection = new BTConnection(bluetoothDevice);
                boolean isConnected = connection.connect();
                setCommandsEnabled(isConnected);
                if (isConnected) {
                    clearOutput();
                    connection.setListener(new TestListener());
                    output.append("Connected to target device\n");
                } else {
                    output.append("Connect failed. Try again\n");
                }
            }
        }
    }

    private void clearOutput() {
        if (output.getEditableText() != null) {
            output.getEditableText().clear();
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

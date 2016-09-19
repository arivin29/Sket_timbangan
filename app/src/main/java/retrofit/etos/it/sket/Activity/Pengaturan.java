package retrofit.etos.it.sket.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit.etos.it.sket.R;

public class Pengaturan extends AppCompatActivity {

    public static String name = null;
    public static String address = null;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    private WifiManager wifiManager;
    ListView devicelist;
    public static String url ="http://arivin.xyz/TM/public/";
    public static String ipMonitor = "http://192.168.1.10";
    public static String monitorPort="3000";

    private EditText textUrl;
    private EditText textIpMonitor;
    private EditText textMonitorPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        textUrl = (EditText) findViewById(R.id.input_url);
        textIpMonitor = (EditText) findViewById(R.id.input_ip_monito);
        textMonitorPort = (EditText) findViewById(R.id.input_monitor_port);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        ImageButton buttonBack = (ImageButton) findViewById(R.id.action_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Wifi

        final TextView wifi = (TextView) findViewById(R.id.wifi);
        Button buttonWifi = (Button) findViewById(R.id.button);
        buttonWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ssid = null;
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if(wifiManager.isWifiEnabled()){
                    final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (connectionInfo != null) {
                        ssid = connectionInfo.getSSID();
                    }
                }else {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    ssid = connectionInfo.getSSID();
                }
                wifi.setText(ssid);
            }
        });

        myBluetooth();

        Button simpan = (Button) findViewById(R.id.simpan_config);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PindahKaMain(address,name);
            }
        });

    }

    private void myBluetooth()
    {
        Button btnPaired = (Button) findViewById(R.id.togle_blutooth);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(myBluetooth == null)
        {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        }
        else if(!myBluetooth.isEnabled())
        {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pairedDevicesList();
            }
        });


    }

    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        List<String> list = new ArrayList<String>();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final CharSequence[] ListBt = list.toArray(new String[list.size()]);
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);
        diaBuilder.setTitle("Pilih Bluetooth");
        diaBuilder.setItems(ListBt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String selected = ListBt[i].toString();
                address = selected.substring(selected.length() - 17);
                name = selected.substring(0, selected.length() - 17);

                if(name != null)
                {
                    TextView TextBtpilih = (TextView) findViewById(R.id.pilhan_bt);
                    TextBtpilih.setText("" + name + "/" + address);
                }


            }
        });

        AlertDialog alertDialog =diaBuilder.create();
        alertDialog.show();

//        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
//        devicelist.setAdapter(adapter);
//        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }

    private void PindahKaMain(String address,String nama)
    {
        url = textUrl.getText().toString();
        ipMonitor = textIpMonitor.getText().toString();
        monitorPort = textMonitorPort.getText().toString();

        Intent intent = new Intent(Pengaturan.this, MainActivity.class);
        intent.putExtra("alamat", address); //this will be received at ledControl (class) Activity
        intent.putExtra("nama", name); //this will be received at ledControl (class) Activity
        startActivity(intent);
    }




    public static String getWifiName(Context context) {
        String ssid = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return null;
        }

        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                ssid = connectionInfo.getSSID();
            }
        }

        return ssid;
    }



}

package retrofit.etos.it.sket.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.etos.it.sket.Adapter.IkanAdaptor;
import retrofit.etos.it.sket.Adapter.TimbangAdaptor;
import retrofit.etos.it.sket.Api.IkanInterface;
import retrofit.etos.it.sket.Api.TimbangInterface;
import retrofit.etos.it.sket.ApiCLient;
import retrofit.etos.it.sket.Model.Ikan;
import retrofit.etos.it.sket.Model.IkanRespon;
import retrofit.etos.it.sket.Model.Timbang;
import retrofit.etos.it.sket.Model.TimbangRespon;
import retrofit.etos.it.sket.R;
import retrofit.etos.it.sket.Service.BluetoothSPP;
import retrofit.etos.it.sket.Service.BluetoothState;
import retrofit.etos.it.sket.Service.DeviceList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ListView listView;
    ListView listView_timbang;

    public String address =null;
    public String nama =null;
    BluetoothSPP bt;
    TextView textStatus, textRead;
    EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar  = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        //ambil setingan
        Intent newIntent = getIntent();
        address = newIntent.getStringExtra("alamat");
        nama = newIntent.getStringExtra("nama");

        BacaBtTm(address,nama);


        //list ikan
        listView = (ListView) findViewById(R.id.list_ikan);
        listView_timbang = (ListView) findViewById(R.id.list_timbang);
        getDataIkan();

        getDataTimbang();
    }

    private void getDataIkan()
    {
        Log.d("mulai", "Cari data");
        IkanInterface ikanInterface = ApiCLient.getClient().create(IkanInterface.class);
        Call<IkanRespon> call = ikanInterface.getData();
        call.enqueue(new Callback<IkanRespon>() {
            @Override
            public void onResponse(Call<IkanRespon> call, Response<IkanRespon> response) {
                Log.d("mulai", "dapat data");
                List<Ikan> ikan = response.body().getListIkan();
                final IkanAdaptor ikanAdaptor = new IkanAdaptor(getApplicationContext(),ikan);
                listView.setAdapter(ikanAdaptor);


                EditText cariIkan = (EditText) findViewById(R.id.cari_ikan);
                cariIkan.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Log.e("pesan", charSequence + "sds");
                        ikanAdaptor.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }

            @Override
            public void onFailure(Call<IkanRespon> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"APlikasi gagal load Data",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void getDataTimbang()
    {
        int id = 1;
        TimbangInterface timbangInterface = ApiCLient.getClient().create(TimbangInterface.class);
        Call<TimbangRespon> call = timbangInterface.getData(id);
        call.enqueue(new Callback<TimbangRespon>() {
            @Override
            public void onResponse(Call<TimbangRespon> call, Response<TimbangRespon> response) {
                List<Timbang> tmb = response.body().getList_timbang();
                TimbangAdaptor adaptor = new TimbangAdaptor(getApplicationContext(),R.id.list_timbang,tmb);

                listView_timbang.setAdapter(adaptor);
            }

            @Override
            public void onFailure(Call<TimbangRespon> call, Throwable t) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.pengaturan)
        {
            Intent i = new Intent(MainActivity.this, Pengaturan.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private void BacaBtTm(String address, String name) {
        bt = new BluetoothSPP(this);

        textRead = (TextView)findViewById(R.id.hasil_timbang);
        textStatus = (TextView)findViewById(R.id.statusBt);

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                textRead.setText(message + "");
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceDisconnected() {
                textStatus.setText("BT Not connect");
//                menu.clear();
//                getMenuInflater().inflate(R.menu.menu_connection, menu);
            }

            public void onDeviceConnectionFailed() {
                textStatus.setText("BT Connection failed");
            }

            public void onDeviceConnected(String name, String address) {
                textStatus.setText("Connected " + name);
//                menu.clear();
//                getMenuInflater().inflate(R.menu.menu_disconnection, menu);
            }
        });

        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            public void onNewConnection(String name, String address) {
                Log.e("Check", "New Connection - " + name + " - " + address);
            }

            public void onAutoConnectionStarted() {
                Log.e("Check", "Auto menu_connection started");
            }
        });

        if(address !=null)
        {
            Log.e("status",address);
            bt.disconnect();
            bt.setupService();
            bt.startService(BluetoothState.DEVICE_ANDROID);
            bt.connect(address);
        }
        textStatus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                    Log.i("MainActivity","Gagal Connnect");
                } else {
                    bt.setDeviceTarget(BluetoothState.DEVICE_OTHER);

                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }

            }
        });

    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
//                DashboardActivity.setup();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
//                DashboardActivity.setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }




}

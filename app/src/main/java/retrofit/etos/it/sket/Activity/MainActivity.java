package retrofit.etos.it.sket.Activity;

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
import android.widget.EditText;
import android.widget.ListView;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ListView listView;
    ListView listView_timbang;

    public IkanAdaptor ikanAdaptor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar  = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


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
            Intent i = new Intent(MainActivity.this, Bluethoot.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}

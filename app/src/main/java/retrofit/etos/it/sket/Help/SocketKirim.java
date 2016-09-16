package retrofit.etos.it.sket.Help;

import android.content.Context;
import android.util.Log;

import com.github.nkzawa.socketio.client.Socket;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit.etos.it.sket.Api.KapalInterface;
import retrofit.etos.it.sket.ApiCLient;
import retrofit.etos.it.sket.Data.DB_kapal;
import retrofit.etos.it.sket.Data.Db_timbang;
import retrofit.etos.it.sket.Model.Kapal;
import retrofit.etos.it.sket.Model.Timbang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by IT on 15/09/2016.
 */
public class SocketKirim {

    public Void kirimHasilKapal(Context context,Socket mSocket, DB_kapal db_kapal, Kapal kapal)
    {
        db_kapal.addKapal(kapal);
        mSocket.emit("send",kapal.toJSON());

        KapalInterface kapalInterface = ApiCLient.getClient().create(KapalInterface.class);
        Call<Kapal> call = kapalInterface.simpanData(kapal);
        call.enqueue(new Callback<Kapal>() {
            @Override
            public void onResponse(Call<Kapal> call, Response<Kapal> response) {

                String str="";

                Log.e("data berhasil di kirm","sds" + str);
            }

            @Override
            public void onFailure(Call<Kapal> call, Throwable t) {
                Log.e("data gagal di kirm","sds");
            }
        });


        return null;
    }

    public Void kirimHasilTimbang(Socket mSocket, Db_timbang db_timbang, Timbang timbang)
    {
        db_timbang.addTimbang(timbang);
        mSocket.emit("send",timbang);
        return null;
    }
}

package retrofit.etos.it.sket.Api;

import retrofit.etos.it.sket.Model.IkanRespon;
import retrofit.etos.it.sket.Model.TimbangRespon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by IT on 06/09/2016.
 */
public interface TimbangInterface {

    @GET("timbang/last")
    Call<TimbangRespon> getData(@Query("id") int id);


}

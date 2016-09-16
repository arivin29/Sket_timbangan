package retrofit.etos.it.sket.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit.etos.it.sket.Interface.Db_kapalInt;
import retrofit.etos.it.sket.Model.Kapal;

/**
 * Created by IT on 15/09/2016.
 */
public class DB_kapal extends SQLiteOpenHelper implements Db_kapalInt {



    public static final String DB_NAME = "Arifin.db";

    public static final String TBL_NAME = "db_kapal";
    public static final String ID = "_id";
    public static final String ID_KAPAL = "id_kapal";
    public static final String NO_INDUK = "no_induk";
    public static final String NO_IZIN = "no_izin";
    public static final String NAMA_KAPAL = "nama_kapal";
    public static final String PEMILIK = "pemilik";
    public static final String ALAT_TANGKAP = "alat_tangkap";
    public static final String STATUS_IZIN = "status_izin";
    public static final String KEY_UNIK = "key_unik";


    public DB_kapal(Context context) {
        super(context, DB_NAME, null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TBL_NAME+" ("
                +ID +" INTEGER PRIMARY KEY,"
                +ID_KAPAL+" TEXT, "
                +NO_INDUK+" TEXT, "
                +NO_IZIN+" TEXT, "
                +NAMA_KAPAL+" TEXT, "
                +PEMILIK+" TEXT, "
                +ALAT_TANGKAP+" TEXT, "
                +STATUS_IZIN+" TEXT,"
                +KEY_UNIK +" TEXT "
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS "+TBL_NAME;
        db.execSQL(sql);
    }

    @Override
    public void addKapal(Kapal kapal) {
        SQLiteDatabase db = this.getWritableDatabase();
//        this.onCreate(db);
        try {

            ContentValues values = new ContentValues();
            values.put(ID_KAPAL, kapal.getIdKapal());
            values.put(NO_INDUK, kapal.getNoInduk());
            values.put(NO_IZIN, kapal.getNoIzin());
            values.put(ALAT_TANGKAP, kapal.getAlatTangkap());
            values.put(NAMA_KAPAL, kapal.getNamaKapal());
            values.put(PEMILIK, kapal.getPemilik());
            values.put(STATUS_IZIN, kapal.getStatusIzin());
            values.put(KEY_UNIK, kapal.getKeyUnik());

            db.insert(TBL_NAME,null,values);
            Log.i("input kapal","berhasil");

            db.close();
        }
        catch (Exception e)
        {
            Log.i("input kapal","Gagal" + e);
        }
    }

    @Override
    public ArrayList<Kapal> getAllKapal() {
        SQLiteDatabase db =this.getReadableDatabase();

        ArrayList<Kapal> kapals = new ArrayList<Kapal>();
        String SQL = "Select * from " + TBL_NAME + " ORDER by " + ID + " DESC";

        Cursor cursor = db.rawQuery(SQL,null);
        if(!cursor.isLast())
        {

//            +ID_KAPAL+" TEXT, "
//                    +NO_INDUK+" TEXT, "
//                    +NO_IZIN+" TEXT, "
//                    +NAMA_KAPAL+" TEXT, "
//                    +PEMILIK+" TEXT, "
//                    +ALAT_TANGKAP +" TEXT, "
//                    +STATUS_IZIN+" TEXT "

              while (cursor.moveToNext())
              {
                  Kapal kapal = new Kapal();
                  if(cursor.getInt(0) < 1)
                  {
                      kapal.setIdKapal(cursor.getInt(0));
                  }
                  else
                  {
                      kapal.setIdKapal(cursor.getInt(1));
                  }
                  kapal.setNoInduk(cursor.getInt(2));
                  kapal.setNoIzin(cursor.getString(3));
                  kapal.setNamaKapal(cursor.getString(4));
                  kapal.setPemilik(cursor.getString(5));
                  kapal.setAlatTangkap(cursor.getString(6));
                  kapal.setStatusIzin(cursor.getInt(7));
                  kapal.setKeyUnik(cursor.getString(8));

                  kapals.add(kapal);
              }
        }
        db.close();
        Log.e("berhasil","data di load");
        return kapals;
    }



    @Override
    public Kapal getKapal(String keyUnik) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL  = "SELECT * FROM " + TBL_NAME + " WHERE " + KEY_UNIK + " like '"+  keyUnik +"' ";
        Cursor cursor = db.rawQuery(SQL,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        Kapal kapal =new Kapal();
        kapal.setNoInduk(cursor.getInt(cursor.getColumnIndex(NO_INDUK)));
        kapal.setNoIzin(cursor.getString(cursor.getColumnIndex(NO_IZIN)));
        kapal.setNamaKapal(cursor.getString(cursor.getColumnIndex(NAMA_KAPAL)));
        kapal.setPemilik(cursor.getString(cursor.getColumnIndex(PEMILIK)));
        kapal.setAlatTangkap(cursor.getString(cursor.getColumnIndex(ALAT_TANGKAP)));
        kapal.setStatusIzin(cursor.getInt(cursor.getColumnIndex(STATUS_IZIN)));
        kapal.setKeyUnik(cursor.getString(cursor.getColumnIndex(KEY_UNIK)));

        return kapal;
    }

    @Override
    public int getKapalCount() {

        SQLiteDatabase db = this.getReadableDatabase();
        int NumRows = (int) DatabaseUtils.queryNumEntries(db,TBL_NAME);

        return NumRows;
    }


}

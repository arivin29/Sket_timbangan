package retrofit.etos.it.sket.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit.etos.it.sket.Interface.Db_timbangInt;
import retrofit.etos.it.sket.Model.Timbang;

/**
 * Created by IT on 15/09/2016.
 *
 *
 */

public class Db_timbang extends SQLiteOpenHelper implements Db_timbangInt {
    public static final String DB_NAME = "Arifin.db";
    public static final String TBL_NAME = "db_timbang";

    public static final String ID = "id_timbang";
    public static final String BERAT = "berat";
    public static final String ID_USER = "id_user";
    public static final String KODE_TIMBANG = "kode_timbang";
    public static final String NAMA_IKAN = "nama_ikan";
    public static final String STATUS_TIMBANG = "status_timbang";
    public static final String TANGGAL_TIMBANG = "tanggal_timbang";
    public static final String SATUAN = "satuan";
    public static final String ID_KAPAL = "id_kapal";
    public static final String ID_IKAN = "id_ikan";
    public static final String UPI = "upi";
    public static final String FAKTOR_B = "faktor_b";
    public static final String FAKTOR_A = "faktor_a";
    public static final String ID_TIMBANG_DETAIL = "id_timbang_detail";
    public static final String HARGA = "harga";
    public static final String KEY_UNIK = "keyUnik";


    public Db_timbang(Context context) {
        super(context, TBL_NAME, null, 1);
    }


    @Override
    public void addTimbang(Timbang timbang) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(BERAT, timbang.getBerat());
            values.put(ID_USER, timbang.getId_user());
            values.put(KODE_TIMBANG, timbang.getBerat());
            values.put(NAMA_IKAN, timbang.getBerat());
            values.put(STATUS_TIMBANG, timbang.getBerat());
            values.put(TANGGAL_TIMBANG, timbang.getBerat());
            values.put(SATUAN, timbang.getBerat());
            values.put(ID_KAPAL, timbang.getBerat());
            values.put(ID_IKAN, timbang.getBerat());
            values.put(UPI, timbang.getBerat());
            values.put(FAKTOR_A, timbang.getBerat());
            values.put(FAKTOR_B, timbang.getBerat());
            values.put(ID_TIMBANG_DETAIL, timbang.getBerat());
            values.put(HARGA, timbang.getBerat());
            values.put(KEY_UNIK, timbang.getBerat());
            db.insert(TBL_NAME,null,values);
            Log.i("com.artonano.kkn.e", "berhasil menambah data!");

            db.close();
        }
        catch (Exception e)
        {
            Log.e("GAGAL","input" + e);
        }
    }

    @Override
    public ArrayList<Timbang> getAllTimbang(int id) {
        return null;
    }

    @Override
    public Timbang getTimbang(int id) {
        return null;
    }

    @Override
    public int getTimbangCount(int id) {
        return 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "CREATE TABLE " + TBL_NAME + "("
                +ID+" INTEGER PRIMARY KEY,"
                +BERAT+" TEXT, "
                +ID_USER+" TEXT, "
                +KODE_TIMBANG+" TEXT, "
                +NAMA_IKAN+" TEXT, "
                +STATUS_TIMBANG+" TEXT, "
                +TANGGAL_TIMBANG+" TEXT, "
                +SATUAN+" TEXT, "
                +ID_KAPAL+" TEXT, "
                +ID_IKAN+" TEXT, "
                +UPI+" TEXT, "
                +FAKTOR_A+" TEXT, "
                +FAKTOR_B+" TEXT, "
                +ID_TIMBANG_DETAIL+" TEXT, "
                +HARGA+" TEXT, "
                +KEY_UNIK+" TEXT "
                +")";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL = "DROP TABLE IF EXISTS "+TBL_NAME;
        db.execSQL(SQL);
    }
}

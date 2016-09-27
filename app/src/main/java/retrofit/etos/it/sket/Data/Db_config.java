package retrofit.etos.it.sket.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by IT on 27/09/2016.
 */
public class Db_config extends SQLiteOpenHelper{

    private static final  String TBL_NAME = "db_config";
    private static final  String CONFIG = "config";
    private static final  String VALUE = "value";
    public static final String ID = "_id";

    public Db_config(Context context) {
        super(context, DB_kapal.DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE "+TBL_NAME+" ("
                +ID +" INTEGER PRIMARY KEY,"
                +CONFIG+" TEXT, "
                +VALUE+" TEXT "
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL = "DROP TABLE IF EXISTS "+TBL_NAME;
        db.execSQL(SQL);
    }

    public String getConfig(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL ="Select * from " + TBL_NAME + " where " + CONFIG + " = ?";
        Cursor cursor = db.rawQuery(SQL,new String[] {key});

        String value = "";
        try{
            if(cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                value = cursor.getString(cursor.getColumnIndex(VALUE)).toString();
            }
            else
            {
                this.addConfig(key,value);
            }

            return value;
        }finally {
           cursor.close();
        }

    }

    public void addConfig(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(CONFIG, key);
            values.put(VALUE, value);

            db.insert(TBL_NAME,null,values);

            db.close();
        }
        catch (Exception e)
        {
            Log.i("input config","Gagal" + e);
        }
    }

    public void editConfig(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(VALUE, value);

            db.update(TBL_NAME,values,"" + CONFIG + " = ? ", new String[] {key.toString()});

            db.close();
        }
        catch (Exception e)
        {
            Log.i("Update config","Gagal" + e);
        }
    }

}

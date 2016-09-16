package retrofit.etos.it.sket.Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit.etos.it.sket.Model.Kapal;
import retrofit.etos.it.sket.Model.Timbang;

/**
 * Created by IT on 15/09/2016.
 */
public interface Db_timbangInt {
    public void addTimbang(Timbang timbang);
    public ArrayList<Timbang> getAllTimbang(int id);
    public Timbang getTimbang(int id);
    public int getTimbangCount(int id);
}

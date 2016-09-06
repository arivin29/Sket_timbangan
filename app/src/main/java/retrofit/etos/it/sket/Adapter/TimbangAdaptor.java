package retrofit.etos.it.sket.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import retrofit.etos.it.sket.Model.Timbang;
import retrofit.etos.it.sket.R;

/**
 * Created by IT on 06/09/2016.
 */
public class TimbangAdaptor extends ArrayAdapter<Timbang> {
    private Context context;
    private List<Timbang> timbangs;

    public TimbangAdaptor(Context context,int resource, List<Timbang> objeks)
    {
        super(context,resource,objeks);
        this.context = context;
        this.timbangs = objeks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_timbang,viewGroup,false);
        Timbang timbang = timbangs.get(position);
        TextView textView = (TextView) view.findViewById(R.id.nomor_urut);
        textView.setText("" + (position + 1));

        TextView id_timbang = (TextView) view.findViewById(R.id.id_timbang);
        id_timbang.setText(timbang.getId_timbang() + " " + timbang.getNama_ikan());

        TextView berat = (TextView) view.findViewById(R.id.details_timbang);
        berat.setText("Berat " + timbang.getBerat() + "Kg");

        return view;
    }
}

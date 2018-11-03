package joaomarcos.aluno.ufop.trabalho;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AtividadeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Atividade> atividades;

    public AtividadeAdapter(Context context, ArrayList<Atividade> atividades) {
        this.context = context;
        this.atividades = atividades;
    }

    @Override
    public int getCount() {
        return atividades.size();
    }

    @Override
    public Object getItem(int position) {
        return atividades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        Atividade atividade = atividades.get(position);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(
                R.layout.activity_atividade_adapter, null);

        //Update layout elements according to the student's info
        TextView tvValor = v.findViewById(R.id.id_adapter_valor);
        tvValor.setText(String.valueOf(df.format(atividade.getValor())));

        TextView tvOrigem = v.findViewById(R.id.id_adapter_origem);
        tvOrigem.setText(atividade.getOrigem());

        TextView tvData = v.findViewById(R.id.id_adapter_data);
        tvData.setText(atividade.getData());

//        TextView tvDescricao = v.findViewById(R.id.id_adapter_descricao);
//        tvDescricao.setText(atividade.getDescricao());

        ImageView iv = v.findViewById(R.id.id_adapter_img_tipo);
        if(atividade.getTipo() == 1)
            iv.setImageResource(R.drawable.plus_button);
        else if(atividade.getTipo() == -1)
            iv.setImageResource(R.drawable.sub_button);

        return v;
    }

}

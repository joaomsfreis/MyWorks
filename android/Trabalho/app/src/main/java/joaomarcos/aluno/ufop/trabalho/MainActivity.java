package joaomarcos.aluno.ufop.trabalho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private ListView lvAtividades;
    private TextView tvEmptyList;
    private TextView tvSaldo;
    private LinearLayout layoutSaldo;
    private LinearLayout layoutCabecalho;
    private int posicao = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedResources.getInstance().loadAtividades(this);
        setContentView(R.layout.activity_main);
        lvAtividades = findViewById(R.id.lvAtividades);
        tvEmptyList = findViewById(R.id.tvListaVazia);
        tvSaldo = findViewById(R.id.tvSaldo);
        layoutSaldo = findViewById(R.id.layoutSaldo);
        layoutCabecalho = findViewById(R.id.layoutCabecalho);
    }

    @Override
    protected void onResume() {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        super.onResume();
        lvAtividades.setAdapter(new AtividadeAdapter(this, SharedResources.getInstance().getAtividades()));
        lvAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Descrição: "+SharedResources.getInstance().getAtividades().get(position).getDescricao(), Toast.LENGTH_SHORT).show();
                posicao = position;
            }
        });
        if(SharedResources.getInstance().getAtividades().isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
            layoutCabecalho.setVisibility(View.INVISIBLE);
        } else {
            tvEmptyList.setVisibility(View.INVISIBLE);
            layoutCabecalho.setVisibility(View.VISIBLE);
        }
        if (SharedResources.getInstance().saldo()<0){
            layoutSaldo.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }else if(SharedResources.getInstance().saldo()>0){
            layoutSaldo.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }else layoutSaldo.setBackgroundColor(getResources().getColor(R.color.colorYellow));

        tvSaldo.setText(String.valueOf(df.format(SharedResources.getInstance().saldo())).replace(".",","));
    }

    public void vaiAddAtividade(View view) {
        Intent it = new Intent(this, AtividadeAdd.class);
        startActivity(it);
    }

    @Override
    protected void onDestroy() {
        SharedResources.getInstance().saveAtividades(this);
        super.onDestroy();
    }

    public void vaiEditAtividade(View view) {
        if (posicao !=-1){
            Intent it = new Intent(getApplicationContext(), AtividadeEdit.class);
            it.putExtra("posicao", posicao);
            startActivity(it);
            posicao=-1;
        }else Toast.makeText(this, R.string.erroALT, Toast.LENGTH_SHORT).show();
    }

    public void vaiDelAtividade(View view) {
        if(posicao!=-1){
            SharedResources.getInstance().getAtividades().remove(posicao);
            SharedResources.getInstance().saveAtividades(this);
            Toast.makeText(this, R.string.confirmaDEL, Toast.LENGTH_SHORT).show();
            posicao=-1;
            onResume();
        }else Toast.makeText(this, R.string.erroALT, Toast.LENGTH_SHORT).show();
    }
}

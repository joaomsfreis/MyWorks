package joaomarcos.aluno.ufop.trabalho;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class AtividadeEdit extends AppCompatActivity {
    private static final String TAG = "AtividadeEdit";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    Atividade atividade;
    int posicao;

    private EditText etOrigem;
    private EditText etValor;
    private EditText etDescricao;
    private TextView tvDia;
    private TextView tvMes;
    private TextView tvAno;
    private RadioButton rbDespesa;
    private RadioButton rbReceita;
    private RadioGroup radioGroup;
    private LinearLayout data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_edit);
        Intent it = getIntent();
        posicao = it.getIntExtra("posicao", 0);

        atividade = SharedResources.getInstance().getAtividades().get(posicao);

        etOrigem = findViewById(R.id.id_origem_edit);
        etValor = findViewById(R.id.id_valor_edit);
        etDescricao = findViewById(R.id.id_descricao_edit);
        tvDia = findViewById(R.id.id_dia_edit);
        tvMes = findViewById(R.id.id_mes_edit);
        tvAno = findViewById(R.id.id_ano_edit);
        rbDespesa = findViewById(R.id.rb_despesa_edit);
        rbReceita = findViewById(R.id.rb_receita_edit);
        radioGroup = findViewById(R.id.rg_tipo_edit);
        data = findViewById(R.id.lldata_edit);

        etOrigem.setText(atividade.getOrigem());
        etValor.setText(String.valueOf(atividade.getValor()));
        etDescricao.setText(atividade.getDescricao());
        tvDia.setText(String.valueOf(atividade.getDia()));
        tvMes.setText(String.valueOf(atividade.getMes()));
        tvAno.setText(String.valueOf(atividade.getAno()));

        if(atividade.getTipo()==1){
            rbReceita.setChecked(true);
        }else rbDespesa.setChecked(true);

        rbReceita.setChecked(true);

        data.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int ano = atividade.getAno();
                int mes = atividade.getMes();
                int dia = atividade.getDia();

                DatePickerDialog dialog = new DatePickerDialog(
                        AtividadeEdit.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener, ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dia = String.valueOf(dayOfMonth);
                String mes = String.valueOf(month+1);
                String ano = String.valueOf(year);
                tvDia.setText(dia);
                tvMes.setText(mes);
                tvAno.setText(ano);
            }
        };


    }

    public void confirmaEdit(View view) {

        if (validaAtividade() != null) {
            SharedResources.getInstance().getAtividades().set(posicao, atividade);
            SharedResources.getInstance().saveAtividades(this);
            Toast.makeText(this, R.string.confirmaALT, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private Atividade validaAtividade(){

        Double valor;
        try{
            valor = Double.parseDouble(etValor.getText().toString().replace(",", "."));
        }catch (Exception e){
            Toast.makeText(this, R.string.erroValor, Toast.LENGTH_SHORT).show();
            etValor.setText("");
            etValor.requestFocus();
            return null;
        }


        String origem = etOrigem.getText().toString();

        if (origem.equals("")){
            Toast.makeText(this, R.string.erroOrigem, Toast.LENGTH_SHORT).show();
            etOrigem.requestFocus();
            return null;
        }

        String descricao = etDescricao.getText().toString();
        int dia = Integer.parseInt(tvDia.getText().toString());
        int mes = Integer.parseInt(tvMes.getText().toString());
        int ano = Integer.parseInt(tvAno.getText().toString());

        int tipo=1;
        if(radioGroup.getCheckedRadioButtonId() == R.id.rb_despesa){
            tipo = -1;
        }else if(radioGroup.getCheckedRadioButtonId() == R.id.rb_receita){
            tipo = 1;
        }

        return new Atividade(valor, dia, mes, ano, origem, descricao, tipo);


    }



}

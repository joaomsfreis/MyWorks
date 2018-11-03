package joaomarcos.aluno.ufop.trabalho;


import android.app.DatePickerDialog;
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


public class AtividadeAdd extends AppCompatActivity {
    private static final String TAG = "AtividadeAdd";
    private DatePickerDialog.OnDateSetListener dateSetListener;

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
        setContentView(R.layout.activity_atividade_add);

        etOrigem = findViewById(R.id.id_origem);
        etValor = findViewById(R.id.id_valor);
        etDescricao = findViewById(R.id.id_descricao);
        tvDia = findViewById(R.id.id_dia);
        tvMes = findViewById(R.id.id_mes);
        tvAno = findViewById(R.id.id_ano);
        rbDespesa = findViewById(R.id.rb_despesa);
        rbReceita = findViewById(R.id.rb_receita);
        radioGroup = findViewById(R.id.rg_tipo);
        data = findViewById(R.id.lldata);

        Calendar cal = Calendar.getInstance();
        tvDia.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        tvMes.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
        tvAno.setText(String.valueOf(cal.get(Calendar.YEAR)));

        rbReceita.setChecked(true);

        data.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int ano = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AtividadeAdd.this,
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

    public void confirmaAdd(View view) {

        if (validaAtividade() != null) {
            SharedResources.getInstance().getAtividades().add(validaAtividade());
            SharedResources.getInstance().saveAtividades(this);

            Toast.makeText(this, R.string.confirmaADD, Toast.LENGTH_SHORT).show();

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

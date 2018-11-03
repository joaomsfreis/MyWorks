package joaomarcos.aluno.ufop.trabalho;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Atividade implements Parcelable, Serializable{

    private double valor;
    private int dia;
    private int mes;
    private int ano;
    private String origem;
    private String descricao;
    private int tipo;

    public Atividade(double valor, int dia, int mes, int ano, String origem, String descricao, int tipo) {
        this.valor = valor;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.origem = origem;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    protected Atividade(Parcel in) {
        valor = in.readDouble();
        dia = in.readInt();
        mes = in.readInt();
        ano = in.readInt();
        origem = in.readString();
        descricao = in.readString();
        tipo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(valor);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(ano);
        dest.writeString(origem);
        dest.writeString(descricao);
        dest.writeInt(tipo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Atividade> CREATOR = new Creator<Atividade>() {
        @Override
        public Atividade createFromParcel(Parcel in) {
            return new Atividade(in);
        }

        @Override
        public Atividade[] newArray(int size) {
            return new Atividade[size];
        }
    };

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getData(){
        return getDia()+"/"+getMes()+"/"+getAno();
    }
}

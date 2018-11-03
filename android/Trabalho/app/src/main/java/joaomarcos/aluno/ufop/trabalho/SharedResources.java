package joaomarcos.aluno.ufop.trabalho;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SharedResources {

    private static SharedResources shared = null;

    //Singleton elements
    private static ArrayList<Atividade> atividades;

    public static SharedResources getInstance() {
        if(shared == null) {
            shared = new SharedResources();
        }
        return shared;
    }

    private SharedResources() {
        atividades = new ArrayList<Atividade>();
    }

    public ArrayList<Atividade> getAtividades() {
        return atividades;
    }

    public void saveAtividades(Context context){
        try {
            FileOutputStream fos = context.openFileOutput("atividades.dat", context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(atividades);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAtividades(Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput("atividades.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            atividades = (ArrayList<Atividade>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public double saldo(){
        double soma = 0;
        for (int i = 0; i < atividades.size(); i++) {
            soma = soma + atividades.get(i).getTipo()*atividades.get(i).getValor();
        }
        return soma;
    }
}

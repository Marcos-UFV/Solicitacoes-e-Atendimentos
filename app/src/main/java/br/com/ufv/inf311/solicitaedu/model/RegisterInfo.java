package br.com.ufv.inf311.solicitaedu.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class RegisterInfo implements Serializable {
    String id;
    String processoNome;
    String momento;
    String etapaNome;
    CamposPersonalizaddosRegistro camposPersonalizados;

    public String getId() {
        return id;
    }

    public String getProcessoNome() {
        return processoNome;
    }

    public String getMomento() {
        return momento;
    }

    public String getEtapaNome() { return etapaNome; }

    public  List<String> getDatas() { return camposPersonalizados.campopersonalizado_11_compl_proc; }

    public  String getDetalhes() { return camposPersonalizados.campopersonalizado_9_compl_proc; }

    public  List<String> getArquivos() { return camposPersonalizados.campopersonalizado_8_compl_proc; }

    public static class CamposPersonalizaddosRegistro implements Serializable {
        List<String> campopersonalizado_11_compl_proc; // Lista de datas
        String campopersonalizado_9_compl_proc; // Detalhes
        List<String> campopersonalizado_8_compl_proc; // Lista de arquivos

    }
}

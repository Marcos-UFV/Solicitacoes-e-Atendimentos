package br.com.ufv.inf311.solicitaedu.model;

import java.io.Serializable;
import java.util.List;

public class AppointmentDTO implements Serializable {
    boolean success;
    AppointmentDTO.Data dados;

    public static class Data {
        List<AppointmentInfo> dados;
    }

    public List<AppointmentInfo> getDados() { return dados.dados; }
}

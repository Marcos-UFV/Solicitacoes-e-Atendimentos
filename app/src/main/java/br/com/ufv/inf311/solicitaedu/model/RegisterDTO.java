package br.com.ufv.inf311.solicitaedu.model;

import java.io.Serializable;
import java.util.List;

public class RegisterDTO implements Serializable {
    boolean success;
    List<RegisterInfo> dados;

    public List<RegisterInfo> getDados() {
        return dados;
    }
}

package br.com.ufv.inf311.solicitaedu.model;

import java.util.List;

public class RequestDTO {
    boolean success;
    List<Data> dados;
    static class Data{
        String id;
        String pessoa;
        String tipo;

        @Override
        public String toString() {
            return "{" +
                    "id='" + id + '\'' +
                    ", pessoa='" + pessoa + '\'' +
                    ", tipo='" + tipo + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "success=" + success +
                ", dados=" + dados +
                '}';
    }
}


package br.com.ufv.inf311.solicitaedu.model;

import java.io.Serializable;
import java.util.List;

public class ContactDTO implements Serializable {
    boolean success;
    List<Data> dados;
    public static class Data{
        String id;
        String nome;
        String datanascimento;
        public String getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getDatanascimento() {
            if(datanascimento != null) {
                String[] split = datanascimento.split("-");
                String day = split[2];
                split[2] = split[0];
                split[0] = day;
                datanascimento = String.join("", split);
            }
            return datanascimento;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", nome='" + nome + '\'' +
                    ", datanascimento='" + datanascimento + '\'' +
                    '}';
        }
    }
    @Override
    public String toString() {
        return "ContactDTO{" +
                "dados='" + dados + '\''+
                "success='" + success ;

    }

    public List<Data> getDados() {
        return dados;
    }
}
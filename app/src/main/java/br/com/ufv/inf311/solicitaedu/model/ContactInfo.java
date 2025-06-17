package br.com.ufv.inf311.solicitaedu.model;

public class ContactInfo {
    String id;
    String nome;
    String datanascimento;

    @Override
    public String toString() {
        return "CatactInfo{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", datanascimento='" + datanascimento + '\'' +
                '}';
    }

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
}

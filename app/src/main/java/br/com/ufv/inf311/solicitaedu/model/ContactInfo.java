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
}

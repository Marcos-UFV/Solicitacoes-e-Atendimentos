package br.com.ufv.inf311.solicitaedu.model;


import java.io.Serializable;

public class Request implements Serializable {
    private int tipo;
    private PersonField pessoa;
    private CustomField camposPersonalizados;
    public Request(int tipo, Contact pessoa,String description) {
        this.tipo = tipo;
        this.pessoa = new PersonField(pessoa.getId());
        this.camposPersonalizados = new CustomField(description);
    }
    private static class PersonField{
        int id;
        public PersonField(int id){
            this.id = id;
        }
    }
    private static class CustomField {
        public CustomField(String description){
            this.campopersonalizado_6_compl_proc = description;
        }
        String campopersonalizado_6_compl_proc;

        @Override
        public String toString() {
            return "{" +
                    "campopersonalizado_6_compl_proc='" + campopersonalizado_6_compl_proc + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "{" +
                "tipo=" + tipo +
                ", pessoa=" + pessoa +
                ", camposPersonalizados=" + camposPersonalizados +
                '}';
    }
}

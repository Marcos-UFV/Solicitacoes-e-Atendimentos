package br.com.ufv.inf311.solicitaedu.model;

public enum RequestType {
    APROVEITAMENTO_DISICPLINA(111,"Solicitação de Aproveitamento de Disciplina"),
    CERTIDAO_DE_VINCULO_ESTUDANTIL(122,"Certidão de que é aluno"),
    SOLICITACAO_EXAME_SUFICIENCIA(112,"Solicitação de Exame de Suficiência"),
    TRANCAMENTO_MATRICULA(123,"Trancamento de Matrícula");
    private int id;
    private String description;

    RequestType(int id, String description) {
        this.id = id;
        this.description = description;
    }
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}

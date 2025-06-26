package br.com.ufv.inf311.solicitaedu.model;

public enum RequestType {
    APROVEITAMENTO_DISICPLINA(111,"Solicitação de Aproveitamento de Disciplina"),
    HISTORICO_ESCOLAR(0,"Histórico Escolar Graduação"),
    CERTIDAO_DE_MATRICULA(0,"Certidão de Matrícula"),
    CERTIDAO_DE_VINCULO_ESTUDANTIL(0,"Certidão de que é aluno"),
    SOLICITACAO_EXAME_SUFICIENCIA(0,"Solicitação de Exame de Suficiência"),
    ALTERACAO_PRE_OU_CO_REQUISITO(0,"Alteração de Pré(co)-Requisito"),
    TRANCAMENTO_MATRICULA(0,"Trancamento de Matrícula");
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

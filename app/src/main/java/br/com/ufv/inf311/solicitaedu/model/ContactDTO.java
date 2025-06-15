package br.com.ufv.inf311.solicitaedu.model;

import java.io.Serializable;
import java.util.List;

public class ContactDTO implements Serializable {
    boolean success;
    List<ContactInfo> dados;
    @Override
    public String toString() {
        return "ContactDTO{" +
                "dados='" + dados + '\''+
                "success='" + success ;

    }
}
/*"success": true,
        "dados": [
        {
        "id": "25",
        "contatoPrincipal": "25",
        "contatosSecundarios": [],
        "nome": "Marcos Aurélio",
        "nomeSocial": null,
        "codigo": null,
        "imagem": null,
        "cpf": null,
        "datanascimento": null,
        "endereco": null,
        "cep": null,
        "numero": null,
        "bairro": null,
        "naturalidade": null,
        "nacionalidade": null,
        "nomeNacionalidade": null,
        "cidade": null,
        "cidadeNome": null,
        "sexo": null,
        "sexoNome": null,
        "origemId": "1",
        "origemNome": "CRM",
        "mesclada": "0",
        "verificada": "1",
        "desinscreveu": "0",
        "naoPermitirComunicacao": "0",
        "urlPublicaRdStation": null,
        "canhoto": null,
        "profissao": null,
        "aluno": null,
        "exaluno": null,
        "anoFormacao": null,
        "escolaOrigem": null,
        "estadoCivil": null,
        "estadoCivilNome": null,
        "cor": null,
        "corNome": null,
        "grauInstrucao": null,
        "grauInstrucaoNome": null,
        "outrasDeficiencias": null,
        "clienteIugu": null,
        "pessoasRelacionadas": false,
        "telefones": {
        "principal": false,
        "secundarios": []
        },
        "emails": {
        "principal": {
        "id": "25",
        "email": "aurelio.marcos@ufv.br"
        },
        "secundarios": []
        },
        "tags": false,
        "origemCanal": false,
        "origens": [
        {
        "id": "1",
        "titulo": "CRM"
        }
        ],
        "tiposPessoas": false,
        "deficiencias": false
        }
        ]
        }*/
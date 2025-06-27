package br.com.ufv.inf311.solicitaedu.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentInfo {
    String contato;
    String descricao;
    String vencimento;
    String duracao;

    public String getNome() { return contato; }

    public String getDescricao() { return descricao; }
    public String getHorario() {
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(vencimento, formatterEntrada);
        LocalDateTime fim = inicio.plusMinutes(Integer.parseInt(duracao));

        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String resultado = String.format(
                "%s - de %s até %s",
                inicio.format(dataFormatter),
                inicio.format(horaFormatter),
                fim.format(horaFormatter)
        );

        return resultado;
    }
}

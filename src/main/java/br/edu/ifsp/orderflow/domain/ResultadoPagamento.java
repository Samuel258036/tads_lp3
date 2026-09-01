package br.edu.ifsp.orderflow.domain;

public class ResultadoPagamento {

    private final boolean aprovado;
    private final String idTrasacao;
    private final String motivo;

    public ResultadoPagamento(boolean aprovado, String idTrasacao, String motivo) {
        this.aprovado = aprovado;
        this.idTrasacao = idTrasacao;
        this.motivo = motivo;
    }

    public static ResultadoPagamento aprovado(String idTrasacao){
        return new ResultadoPagamento(true, idTrasacao, null);
    }

    public static ResultadoPagamento recusado(String motivo) {
        return new ResultadoPagamento(false, null, motivo);

    }

    public boolean isAprovado() {
        return this.aprovado;
    }

    public  String getIdTrasacao() {
        return this.idTrasacao;
    }

    public String getMotivo() {
        return this.motivo;
    }
}

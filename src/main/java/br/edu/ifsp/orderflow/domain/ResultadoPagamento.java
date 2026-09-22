package br.edu.ifsp.orderflow.domain;

public class ResultadoPagamento {

    private final boolean aprovado;
    private final String idTransacao;
    private final String motivo;

    public ResultadoPagamento(boolean aprovado, String idTransacao, String motivo) {
        this.aprovado = aprovado;
        this.idTransacao = idTransacao;
        this.motivo = motivo;
    }

    public static ResultadoPagamento aprovado(String idTransacao) {
        return new ResultadoPagamento(true, idTransacao, null);
    }

    public static ResultadoPagamento recusado(String motivo) {
        return new ResultadoPagamento(false, null, motivo);
    }

    public boolean isAprovado() {
        return this.aprovado;
    }

    public String getIdTransacao() {
        return this.idTransacao;
    }

    public String getMotivo() {
        return this.motivo;
    }
}

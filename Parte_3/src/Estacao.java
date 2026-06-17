public class Estacao extends Espaco {
    private boolean monitorExtra;
    private static double precoMonitor;

    public Estacao(String descricao, double valorHora, double taxaLimpeza, boolean monitorExtra, double precoMonitor) {
        super(descricao, valorHora, taxaLimpeza);
        this.setMonitorExtra(monitorExtra);
        this.setPrecoMonitor(precoMonitor);
    }

    public boolean getMonitorExtra() {
        return monitorExtra;
    }

    public void setMonitorExtra(boolean monitorExtra) {
        this.monitorExtra = monitorExtra;
    }

    public double getPrecoMonitor() {
        return precoMonitor;
    }

    public void setPrecoMonitor(double precoMonitor) {
        Estacao.precoMonitor = precoMonitor;
    }

    @Override
    public double preco(Horario inicio, Horario fim) {
        double valor = super.preco(inicio, fim);
        if (this.monitorExtra) valor += precoMonitor;
        return valor;
    }

    @Override
    public boolean possuiAdicionalExtra() {
        return this.monitorExtra;
    }

    @Override
    public String toString() {
        if (this.monitorExtra) return this.descricao + " (Estacao de Trabalho com Monitor Extra)";
        return this.descricao + " (Estacao de Trabalho sem Monitor Extra)";
    }
}
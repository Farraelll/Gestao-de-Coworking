import java.util.ArrayList;

public abstract class Espaco implements Reservavel{
    protected String descricao;
    protected static double valorHora, taxaLimpeza;
    protected ArrayList<Reserva> reservas;

    public Espaco(String descricao, double valorHora, double taxaLimpeza) {
        this.setDescricao(descricao);
        this.setValorHora(valorHora);
        this.setTaxaLimpeza(taxaLimpeza);
        this.reservas = new ArrayList<>();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        Espaco.valorHora = valorHora;
    }

    public double getTaxaLimpeza() {
        return taxaLimpeza;
    }

    public void setTaxaLimpeza(double taxaLimpeza) {
        Espaco.taxaLimpeza = taxaLimpeza;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void adicionarReserva(Reserva r) {
        this.reservas.add(r);
}
    
    public boolean disponivel(Data d, Horario inicio, Horario fim, boolean extra) {
        if (this.possuiAdicionalExtra() != extra) return false;

        for (Reserva r : this.reservas) {
            if (r.getData().compara(d)) {
                Horario rIni = r.getInicio();
                Horario rFim = r.getFim();
                boolean naoConflita = fim.compara(rIni) <= 0 || rFim.compara(inicio) <= 0;
                if (!naoConflita) return false;
            }
        }
        return true;
    }

    public double preco(Horario inicio, Horario fim) {
        int minutos = (fim.getHora() * 60 + fim.getMin()) - (inicio.getHora() * 60 + inicio.getMin());
        int horas = (int) Math.ceil(minutos / 60.0);
        return valorHora * horas + taxaLimpeza;
    }

    public abstract boolean possuiAdicionalExtra();

    @Override
    public abstract String toString();
}
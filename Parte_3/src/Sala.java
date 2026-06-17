public class Sala extends Espaco {
    private boolean projetor;
    private static double precoProjetor;

    public Sala(String descricao, double valorHora, double taxaLimpeza, boolean projetor, double precoProjetor) {
        super(descricao, valorHora, taxaLimpeza);
        this.setProjetor(projetor);
        this.setPrecoProjetor(precoProjetor);
    }

    public boolean getProjetor() {
        return projetor;
    }

    public void setProjetor(boolean projetor) {
        this.projetor = projetor;
    }

    public double getPrecoProjetor() {
        return precoProjetor;
    }

    public void setPrecoProjetor(double precoProjetor) {
        Sala.precoProjetor = precoProjetor;
    }

    @Override
    public double preco(Horario inicio, Horario fim) {
        double valor = 4 * super.preco(inicio, fim);
        if (this.projetor) valor += precoProjetor;
        return valor;
    }

    @Override
    public boolean possuiAdicionalExtra() {
        return this.projetor;
    }

    @Override
    public String toString() {
        if (this.projetor) return this.descricao + " (Sala com Projetor)";
        return this.descricao + " (Sala sem Projetor)";
    }
}
public class Reserva {
    private Data d;
    private Horario inicio, fim;
    private Espaco esp;
    private Cliente cli;

    public Reserva(Data d, Horario inicio, Horario fim, Espaco esp, Cliente cli) {
        this.setData(d);
        this.setInicio(inicio);
        this.setFim(fim);
        this.setEspaco(esp);
        this.setCliente(cli);
    }

    public Data getData() {
        return d;
    }

    public void setData(Data d) {
        this.d = d;
    }

    public Horario getInicio() {
        return inicio;
    }

    public void setInicio(Horario inicio) {
        this.inicio = inicio;
    }

    public Horario getFim() {
        return fim;
    }

    public void setFim(Horario fim) {
        this.fim = fim;
    }

    public Espaco getEspaco() {
        return esp;
    }

    public void setEspaco(Espaco esp) {
        this.esp = esp;
    }

    public Cliente getCliente() {
        return cli;
    }

    public void setCliente(Cliente cli) {
        this.cli = cli;
    }

    public double preco() {
        return this.esp.preco(this.inicio, this.fim);
    }

    @Override
    public String toString() {
        return "Reserva:\n"
                + "* Local: " + this.esp + "\n"
                + "* Data: " + this.d + "\n"
                + "* Horario: " + this.inicio + " - " + this.fim + "\n"
                + "* Cliente: " + this.cli + "\n"
                + String.format("* Valor: R$ %.2f", this.preco());
    }
}
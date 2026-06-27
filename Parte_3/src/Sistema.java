import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

public class Sistema {
    private LinkedHashMap<String, Cliente> clientes;
    private ArrayList<Reservavel> salas;
    private ArrayList<Reservavel> estacoes;
    private double valorHora, taxaLimpeza, precoProjetor, precoMonitor;

    public Sistema(double valorHora, double taxaLimpeza, double precoProjetor, double precoMonitor) {
        this.clientes = new LinkedHashMap<>();
        this.salas = new ArrayList<>();
        this.estacoes = new ArrayList<>();
        this.valorHora = valorHora;
        this.taxaLimpeza = taxaLimpeza;
        this.precoProjetor = precoProjetor;
        this.precoMonitor = precoMonitor;
    }

    public double getValorHora() {
        return valorHora;
    }

    public double getTaxaLimpeza() {
        return taxaLimpeza;
    }

    public double getPrecoProjetor() {
        return precoProjetor;
    }

    public double getPrecoMonitor() {
        return precoMonitor;
    }

    public Collection<Cliente> getClientes() {
        return clientes.values();
    }

    public ArrayList<Reservavel> getSalas() {
        return salas;
    }

    public ArrayList<Reservavel> getEstacoes() {
        return estacoes;
    }

    public Cliente getCliente(String cpf) {
        return this.clientes.get(cpf);
    }

    public void cadastrar(Cliente cli) {
        this.clientes.put(cli.getCpf(), cli);
    }

    public void cadastrar(Sala s) {
        this.salas.add(s);
    }

    public void cadastrar(Estacao e) {
        this.estacoes.add(e);
    }

    public boolean reservar(String tipo, Data d, Horario inicio, Horario fim, Cliente c, boolean extra) {
        if (c == null) return false;

        ArrayList<Reservavel> lista;
        if (tipo.equalsIgnoreCase("s")) lista = this.salas;
        else lista = this.estacoes;

        for (Reservavel e : lista) {
            if (e.disponivel(d, inicio, fim, extra)) {
                Reserva r = new Reserva(d, inicio, fim, e, c);
                e.adicionarReserva(r);
                return true;
            }
        }
        return false;
    }

    public boolean reservar(String tipo, Data d, Cliente c, boolean extra) {
        return this.reservar(tipo, d, new Horario(8, 0), new Horario(22, 0), c, extra);
    }

    public boolean reservar(String tipo, Data d, String turno, Cliente c, boolean extra) {
        Horario inicio;
        Horario fim;
        switch (turno.toLowerCase()) {
            case "m":
            case "matutino":
                inicio = new Horario(8, 0);
                fim = new Horario(12, 0);
                break;
            case "v":
            case "vespertino":
                inicio = new Horario(13, 0);
                fim = new Horario(17, 0);
                break;
            case "n":
            case "noturno":
                inicio = new Horario(18, 0);
                fim = new Horario(22, 0);
                break;
            default:
                return false;
        }
        return this.reservar(tipo, d, inicio, fim, c, extra);
    }

    public ArrayList<Reserva> getReservas() {
        ArrayList<Reserva> todas = new ArrayList<>();
        for (Reservavel e : this.salas) todas.addAll(e.getReservas());
        for (Reservavel e : this.estacoes) todas.addAll(e.getReservas());
        return todas;
    }

    public ArrayList<Reserva> getReservas(Data d) {
        ArrayList<Reserva> filtradas = new ArrayList<>();
        for (Reserva r : this.getReservas()) {
            if (r.getData().compara(d)) filtradas.add(r);
        }
        return filtradas;
    }

    public ArrayList<Reserva> getReservas(Cliente c) {
        ArrayList<Reserva> filtradas = new ArrayList<>();
        if (c == null) return filtradas;
        for (Reserva r : this.getReservas()) {
            if (r.getCliente().getCpf().equals(c.getCpf())) filtradas.add(r);
        }
        return filtradas;
    }
}
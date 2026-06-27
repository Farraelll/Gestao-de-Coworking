import java.util.ArrayList;

public interface Reservavel {
    boolean disponivel(Data d, Horario inicio, Horario fim, boolean extra);
    void adicionarReserva(Reserva r);
    ArrayList<Reserva> getReservas();
    double preco(Horario inicio, Horario fim);
    boolean possuiAdicionalExtra();
}

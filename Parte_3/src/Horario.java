public class Horario {
    private int hora, min;

    public Horario(int hora, int min) {
        this.setHora(hora);
        this.setMin(min);
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int compara(Horario h2) {
        int t1 = this.hora * 60 + this.min;
        int t2 = h2.hora * 60 + h2.min;
        if (t1 < t2) return -1;
        if (t1 > t2) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", this.hora, this.min);
    }
}
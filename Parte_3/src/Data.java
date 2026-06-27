public class Data {
    private int dia, mes, ano;

    public Data(int dia, int mes, int ano) {
        this.setDia(dia);
        this.setMes(mes);
        this.setAno(ano);
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        if (dia < 1 || dia > 31) {
            throw new IllegalArgumentException("Dia inválido: " + dia + " (deve estar entre 1 e 31).");
        }
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês inválido: " + mes + " (deve estar entre 1 e 12).");
        }
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        if (ano < 1) {
            throw new IllegalArgumentException("Ano inválido: " + ano + " (deve ser maior que 0).");
        }
        this.ano = ano;
    }

    public boolean compara(Data d2) {
        return this.dia == d2.dia && this.mes == d2.mes && this.ano == d2.ano;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", this.dia, this.mes, this.ano);
    }
}
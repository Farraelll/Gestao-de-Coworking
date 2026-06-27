import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;

/** Classe com as rotinas de entrada e saída do projeto
 * @authors Hilario Seibel Junior, Rafael Marques Silva e Ilanna dos Reis Cardoso
 */
public class Entrada {
    public Scanner input;

    /** Construtor da classe Entrada
     * Se houver um arquivo input.txt na pasta em que o projeto está sendo executado,
     * define que o Scanner vai ler deste arquivo e não do teclado.
     * Se o arquivo não existir, define que o Scanner vai ler da entrada padrão (teclado)
     * NÃO ALTERE O CODIGO DESTE CONSTRUTOR!!!!
     */
    public Entrada() {
        try {
            this.input = new Scanner(new FileInputStream("input.txt")).useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            this.input = new Scanner(System.in).useLocale(Locale.US);
        }
    }
	
    public String lerLinha(String msg) {
        System.out.print(msg);
        String linha = this.input.nextLine();
		
        while (linha.isEmpty() || linha.charAt(0) == '#') linha = this.input.nextLine();
        return linha;
    }
	
    public int lerInteiro(String msg) {
        String linha = this.lerLinha(msg);
        try {
            return Integer.parseInt(linha.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("'" + linha + "' não é um número inteiro válido.");
        }
    }

    private double lerDouble(String msg) {
        String linha = this.lerLinha(msg);
        try {
            return Double.parseDouble(linha.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("'" + linha + "' não é um número válido.");
        }
    }
	
    public Data lerData(Sistema s) {
        System.out.println("Escolha uma data (dd/mm/aaaa):");
        int dia = this.lerInteiro("Dia: ");
        int mes = this.lerInteiro("Mês: ");
        int ano = this.lerInteiro("Ano: ");
        return new Data(dia, mes, ano);
    }

    public Horario lerHorario(Sistema s) {
        System.out.println("Escolha um horário (hh:mm):");
        int hora = this.lerInteiro("Hora: ");
        int min = this.lerInteiro("Minuto: ");
        return new Horario(hora, min);
    }

    public String lerTipo(Sistema s) {
        String tipo = this.lerLinha("Deseja reservar uma sala ou estação de trabalho? (s/e): ");
        
        while (!tipo.equalsIgnoreCase("s") && !tipo.equalsIgnoreCase("e")) {
            System.out.println("Opção inválida.");
            tipo = this.lerLinha("Digite 's' para sala ou 'e' para estação de trabalho: ");
        }
        
        return tipo;
    }

    public boolean lerExtra(String tipo) {
        String resp;
        if (tipo.equalsIgnoreCase("s")) {
            resp = this.lerLinha("Deseja reservar sala com projetor? (s/n): ");
        }
        else {
            resp = this.lerLinha("Deseja reservar estação de trabalho com monitor extra? (s/n): ");
        }
        return resp.equalsIgnoreCase("s");
    }

    public Cliente lerCliente(Sistema s) {
        this.listarClientes(s);
        String cpf = this.lerLinha("Digite o CPF do cliente: ");
        return s.getCliente(cpf);
    }

    public void fechar() {
        this.input.close();
    }
	
    public int menu() {
        String msg = """
		        *********************
		        Escolha uma opção:
		        1) Cadastros
		        2) Reservas
		        0) Sair
		        """;

        int op = this.lerInteiro(msg);

        while (op < 0 || op > 2) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }

        return op;
    }

    public void menuCadastro(Sistema s) {
        String msg = """
		        *********************
		        Escolha uma opção:
		        1) Ver clientes
		        2) Ver salas
		        3) Ver estações de trabalho
		        4) Cadastrar cliente
		        5) Cadastrar sala
		        6) Cadastrar estação de trabalho
		        0) Voltar
		        """;

        int op = this.lerInteiro(msg);

        while (op < 0 || op > 6) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }

        switch (op) {
            case 1:
                this.listarClientes(s);
                break;
            case 2:
                this.listarSalas(s);
                break;
            case 3:
                this.listarEstacoes(s);
                break;
            case 4:
                this.cadastrarCliente(s);
                break;
            case 5:
                this.cadastrarSala(s);
                break;
            case 6:
                this.cadastrarEstacao(s);
                break;
        }
    }

    public void menuReservas(Sistema s) {
        String msg = """
		        *********************
		        Escolha uma opção:
		        1) Ver reservas
		        2) Ver reservas por data
		        3) Ver reservas por cliente
		        4) Fazer reserva (dia inteiro)
		        5) Fazer reserva (turno inteiro)
		        6) Fazer reserva (horário específico)
		        0) Voltar
		        """;

        int op = this.lerInteiro(msg);

        while (op < 0 || op > 6) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }

        switch (op) {
            case 1:
                this.listarReservas(s);
                break;
            case 2:
                this.listarReservasData(s);
                break;
            case 3:
                this.listarReservasCliente(s);
                break;
            case 4:
                this.reservarData(s);
                break;
            case 5:
                this.reservarTurno(s);
                break;
            case 6:
                this.reservarHorario(s);
                break;
        }
    }

    public Sistema criarSistema() {
        Sistema dados = Persistencia.carregar("dados");
        if (dados != null) {
            System.out.println("Dados carregados do arquivo \"dados\".");
            return dados;
        }

        System.out.println("Iniciando o sistema...");
        double valorHora = this.lerDouble("Digite o valor por hora para usar um espaço: R$ ");
        double taxaLimpeza = this.lerDouble("Digite a taxa de limpeza: R$ ");
        double precoProjetor = this.lerDouble("Digite o valor extra para usar o projetor: R$ ");
        double precoMonitor = this.lerDouble("Digite o valor para usar o monitor extra: R$ ");

        return new Sistema(valorHora, taxaLimpeza, precoProjetor, precoMonitor);
    }

    public void listarClientes(Sistema s) {
        System.out.println("*********************************");
        Collection<Cliente> clientes = s.getClientes();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        }
        else {
            System.out.println("Clientes cadastrados:");
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }

    public void listarSalas(Sistema s) {
        System.out.println("*********************************");
        ArrayList<Reservavel> salas = s.getSalas();

        if (salas.isEmpty()) {
            System.out.println("Nenhuma sala cadastrada.");
        }
        else {
            System.out.println("Salas cadastradas:");
            for (Reservavel sala : salas) {
                System.out.println(sala);
            }
        }
    }

    public void listarEstacoes(Sistema s) {
        System.out.println("*********************************");
        ArrayList<Reservavel> estacoes = s.getEstacoes();

        if (estacoes.isEmpty()) {
            System.out.println("Nenhuma estação de trabalho cadastrada.");
        }
        else {
            System.out.println("Estações de Trabalho cadastradas:");
            for (Reservavel e : estacoes) {
                System.out.println(e);
            }
        }
    }

    public void listarReservas(Sistema s) {
        ArrayList<Reserva> reservas = s.getReservas();

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
        }
        else {
            System.out.println("Reservas cadastradas:");
            for (Reserva r : reservas) {
                System.out.println(r);
            }
        }
    }

    public void listarReservasData(Sistema s) {
        Data d = this.lerData(s);
        ArrayList<Reserva> reservas = s.getReservas(d);

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada nesta data.");
        }
        else {
            System.out.println("Reservas cadastradas nesta data:");
            for (Reserva r : reservas) {
                System.out.println(r);
            }
        }
    }

    public void listarReservasCliente(Sistema s) {
        Cliente c = this.lerCliente(s);
        if (c == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        ArrayList<Reserva> reservas = s.getReservas(c);

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada para este cliente.");
        }
        else {
            System.out.println("Reservas cadastradas para este cliente:");
            for (Reserva r : reservas) {
                System.out.println(r);
            }
        }
    }

    public void cadastrarCliente(Sistema s) {
        this.listarClientes(s);

        System.out.println("Cadastrando cliente.");
        String nome = this.lerLinha("Digite o nome do cliente: ");
        String cpf = this.lerLinha("Digite o CPF do cliente: ");
        String email = this.lerLinha("Digite o email do cliente: ");
        String senha = this.lerLinha("Digite a senha do cliente: ");

        if (s.getCliente(cpf) == null) {
            s.cadastrar(new Cliente(nome, cpf, email, senha));
        }
        else {
            System.out.println("CPF já cadastrado. Cliente não inserido.");
        }
    }

    public void cadastrarSala(Sistema s) {
        this.listarSalas(s);

        System.out.println("Cadastrando sala.");
        String nome = this.lerLinha("Digite o nome da sala: ");
        String resp = this.lerLinha("Possui projetor? (s/n): ");
        boolean projetor = resp.equalsIgnoreCase("s");

        Sala sala = new Sala(nome, s.getValorHora(), s.getTaxaLimpeza(), projetor, s.getPrecoProjetor());
        s.cadastrar(sala);
    }

    public void cadastrarEstacao(Sistema s) {
        this.listarEstacoes(s);

        System.out.println("Cadastrando estação de trabalho.");
        String nome = this.lerLinha("Digite o nome da estação de trabalho: ");
        String resp = this.lerLinha("Possui monitor extra? (s/n): ");
        boolean monitor = resp.equalsIgnoreCase("s");

        Estacao est = new Estacao(nome, s.getValorHora(), s.getTaxaLimpeza(), monitor, s.getPrecoMonitor());
        s.cadastrar(est);
    }

    public void reservarData(Sistema s) {
        String tipo = this.lerTipo(s);
        boolean extra = this.lerExtra(tipo);
        Data d = this.lerData(s);
        Cliente c = this.lerCliente(s);

        boolean disp = s.reservar(tipo, d, c, extra);

        if (disp) System.out.println("Reserva realizada com sucesso!");
        else System.out.println("Reserva não realizada.");
    }

    public void reservarTurno(Sistema s) {
        String tipo = this.lerTipo(s);
        boolean extra = this.lerExtra(tipo);
        Data d = this.lerData(s);
        String turno = this.lerLinha("Escolha um turno: matutino, vespertino ou noturno (m/v/n): ");
        Cliente c = this.lerCliente(s);
        
        boolean disp = s.reservar(tipo, d, turno, c, extra);

        if (disp) System.out.println("Reserva realizada com sucesso!");
        else System.out.println("Reserva não realizada.");
    }

    public void reservarHorario(Sistema s) {
        String tipo = this.lerTipo(s);
        boolean extra = this.lerExtra(tipo);
        Data d = this.lerData(s);
        Horario inicio = this.lerHorario(s);
        Horario fim = this.lerHorario(s);
        Cliente c = this.lerCliente(s);

        boolean disp = s.reservar(tipo, d, inicio, fim, c, extra);

        if (disp) System.out.println("Reserva realizada com sucesso!");
        else System.out.println("Reserva não realizada.");
    }
}
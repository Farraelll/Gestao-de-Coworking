import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/** Classe responsável por salvar e carregar os dados do sistema em arquivo
 * O arquivo é dividido em seções, cada campo separado por ';':
 *   CONFIG   -> valorHora;taxaLimpeza;precoProjetor;precoMonitor
 *   CLIENTES -> nome;cpf;email;senha
 *   SALAS    -> descricao;projetor
 *   ESTACOES -> descricao;monitorExtra
 *   RESERVAS -> tipo;indiceEspaco;dia;mes;ano;horaIni;minIni;horaFim;minFim;cpfCliente
 */
public class Persistencia {
    private static final String SEP = ";";

    /** Salva todas as informações do sistema no arquivo informado.
     * @param s sistema a ser salvo
     * @param arquivo nome do arquivo de destino
     */
    public static void salvar(Sistema s, String arquivo) {
        try (PrintWriter out = new PrintWriter(new FileWriter(arquivo))) {
            out.println("CONFIG");
            out.println(s.getValorHora() + SEP + s.getTaxaLimpeza() + SEP
                    + s.getPrecoProjetor() + SEP + s.getPrecoMonitor());

            out.println("CLIENTES");
            for (Cliente c : s.getClientes()) {
                out.println(c.getNome() + SEP + c.getCpf() + SEP + c.getEmail() + SEP + c.getSenha());
            }

            out.println("SALAS");
            for (Reservavel r : s.getSalas()) {
                Sala sala = (Sala) r;
                out.println(sala.getDescricao() + SEP + sala.getProjetor());
            }

            out.println("ESTACOES");
            for (Reservavel r : s.getEstacoes()) {
                Estacao est = (Estacao) r;
                out.println(est.getDescricao() + SEP + est.getMonitorExtra());
            }

            out.println("RESERVAS");
            ArrayList<Reservavel> salas = s.getSalas();
            ArrayList<Reservavel> estacoes = s.getEstacoes();
            for (Reserva res : s.getReservas()) {
                String tipo;
                int indice;
                if (salas.contains(res.getEspaco())) {
                    tipo = "s";
                    indice = salas.indexOf(res.getEspaco());
                } else {
                    tipo = "e";
                    indice = estacoes.indexOf(res.getEspaco());
                }
                Data d = res.getData();
                Horario ini = res.getInicio();
                Horario fim = res.getFim();
                out.println(tipo + SEP + indice + SEP
                        + d.getDia() + SEP + d.getMes() + SEP + d.getAno() + SEP
                        + ini.getHora() + SEP + ini.getMin() + SEP
                        + fim.getHora() + SEP + fim.getMin() + SEP
                        + res.getCliente().getCpf());
            }

            System.out.println("Dados salvos no arquivo \"" + arquivo + "\".");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    /** Carrega as informações do sistema a partir do arquivo informado.
     * @param arquivo nome do arquivo de origem
     * @return o sistema reconstruído, ou null se o arquivo não existir ou estiver inválido
     */
    public static Sistema carregar(String arquivo) {
        try (BufferedReader in = new BufferedReader(new FileReader(arquivo))) {
            String linha = in.readLine();
            if (linha == null) return null;

            // CONFIGURAÇÕES
            String[] config = in.readLine().split(SEP);
            double valorHora = Double.parseDouble(config[0]);
            double taxaLimpeza = Double.parseDouble(config[1]);
            double precoProjetor = Double.parseDouble(config[2]);
            double precoMonitor = Double.parseDouble(config[3]);
            Sistema s = new Sistema(valorHora, taxaLimpeza, precoProjetor, precoMonitor);

            in.readLine(); // pula o cabeçalho CLIENTES
            while ((linha = in.readLine()) != null && secao(linha)) {
                String[] c = linha.split(SEP);
                s.cadastrar(new Cliente(c[0], c[1], c[2], c[3]));
            }
	        
	        // SALAS
	        while ((linha = in.readLine()) != null && secao(linha)) {
                String[] sl = linha.split(SEP);
                boolean projetor = Boolean.parseBoolean(sl[1]);
                s.cadastrar(new Sala(sl[0], valorHora, taxaLimpeza, projetor, precoProjetor));
            }
	        
	        // ESTACOES
	        while ((linha = in.readLine()) != null && secao(linha)) {
                String[] es = linha.split(SEP);
                boolean monitor = Boolean.parseBoolean(es[1]);
                s.cadastrar(new Estacao(es[0], valorHora, taxaLimpeza, monitor, precoMonitor));
            }
	        
	        // "RESERVAS"
	        while ((linha = in.readLine()) != null && secao(linha)) {
                String[] r = linha.split(SEP);
                String tipo = r[0];
                int indice = Integer.parseInt(r[1]);
                Data d = new Data(Integer.parseInt(r[2]), Integer.parseInt(r[3]), Integer.parseInt(r[4]));
                Horario ini = new Horario(Integer.parseInt(r[5]), Integer.parseInt(r[6]));
                Horario fim = new Horario(Integer.parseInt(r[7]), Integer.parseInt(r[8]));
                Cliente cli = s.getCliente(r[9]);

                Reservavel esp;
                if (tipo.equals("s")) esp = s.getSalas().get(indice);
                else esp = s.getEstacoes().get(indice);

                esp.adicionarReserva(new Reserva(d, ini, fim, esp, cli));
            }

            return s;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
            return null;
        }
    }

    private static boolean secao(String linha) {
        return !linha.equals("CONFIG") && !linha.equals("CLIENTES")
                && !linha.equals("SALAS") && !linha.equals("ESTACOES")
                && !linha.equals("RESERVAS");
    }
}

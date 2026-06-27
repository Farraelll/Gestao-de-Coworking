void main() {
	Entrada e = new Entrada();
	Sistema s = e.criarSistema();
	
	int op = -1;
	while (op != 0) {
		try {
			op = e.menu();
			switch (op) {
				case 1:
					e.menuCadastro(s);
					break;
				case 2:
					e.menuReservas(s);
					break;
			}
		} catch (java.util.NoSuchElementException ex) {
			op = 0;
		} catch (Exception ex) {
			System.out.println("Erro: " + ex.getMessage());
			op = -1;
		}
	}
	Persistencia.salvar(s, "dados");
	e.fechar();
}
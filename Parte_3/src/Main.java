void main() {
	Entrada e = new Entrada();
	Sistema s = e.criarSistema();
	
	int op = e.menu();
	
	while (op != 0) {
		switch (op) {
			case 1:
				e.menuCadastro(s);
				break;
			case 2:
				e.menuReservas(s);
				break;
		}
		op = e.menu();
	}
	e.fechar();
}
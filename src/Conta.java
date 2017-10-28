import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conta {

	/*	PARTE ESTATICA */

	public static final String[] Operacoes = {"SAQUE","DEPOSITO","SALDO","EXTRATO"};
	public static enum Operacao {
		SAQUE, DEPOSITO, SALDO, EXTRATO
	}
	public static final String codigoCC = "-0";
	public static final String codigoPP = "-1";
	
	public static boolean isIDparcial( String id ) {
		return id.matches( "[0-9]{3}" );//(-[01])?
	}

	public static boolean isIDcompleto( String id ) {
		return id.matches( "[0-9]{3}-[01]" );
	}

	public static String sayTipo( String id ) {
		if( isIDparcial(id) ) {
			String tipo = id.substring(id.length() - 2);
			if( tipo.equals( codigoCC ) ) return "Corrente";
			else if( tipo.equals( codigoPP ) ) return "Poupanca";
		}
		return "Invalida";
	}





	/* PARTE INSTACIAVEL */

	private String id;
	private double total;
	private List<Movimentacao> extrato;
	private Map<LocalDateTime, Movimentacao> historico;
	
	public Conta( String id ) {
		this.id = id;
		extrato = new ArrayList<Movimentacao>();
		historico = new HashMap<LocalDateTime, Movimentacao>();
	}

	public String getId() {
		return this.id;
	}

	public double saque( double valor ) {
		if( total > valor ) {
			total -= valor;
			Movimentacao m = new Movimentacao(valor, total, Conta.Operacao.SAQUE, null);
			extrato.add( m );
			return valor;
		}
		return 0;
	}

	public boolean deposito( double valor ) {
		if( valor > 0 ) {
			total += valor;
			extrato.add( new Movimentacao(valor, total, Conta.Operacao.DEPOSITO, null));
			return true;
		}
		return false;
	}

	public double saldo() {
		return total;
	}

	public void Extrato() {

	}

}





// CLASSE AUXILIARES

class Movimentacao {
	public LocalDateTime data;
	public double valor;
	public double saldo;
	public int operacao;
	public String parametro;

	public Movimentacao(double valor, double total, Conta.Operacao operacao, String parametro) {
		this.saldo = total + valor;
		this.data = LocalDateTime.now();
		this.valor = valor;
		this.parametro = parametro;
	}
}
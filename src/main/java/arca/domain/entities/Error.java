package arca.domain.entities;

public class Error extends JsonModel {

	public  Erro erro;
	public static class Erro{
		public String codigo;
		public String mensagem;
	}
}


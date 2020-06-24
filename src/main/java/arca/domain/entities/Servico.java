package arca.domain.entities;

import java.io.Serializable;

public class Servico implements Serializable {
	private static final long serialVersionUID = 1L;

	public String mensagemServico;
	public String grupoConexao;
	public String sequencia;
	public String servico;
	public String grupo;
	public String saida;
	public String chegada;
	public Integer poltronasLivres;
	public Integer poltronasTotal;
	public String preco;
	public String classe;
	public String empresa;
	public Boolean vende;
	public Float km;
	public Conexao conexao;

	@Override
	public String toString() {
		return "Servico{" +
				"mensagemServico='" + mensagemServico + '\'' +
				", grupoConexao='" + grupoConexao + '\'' +
				", sequencia='" + sequencia + '\'' +
				", servico='" + servico + '\'' +
				", grupo='" + grupo + '\'' +
				", saida='" + saida + '\'' +
				", chegada='" + chegada + '\'' +
				", poltronasLivres=" + poltronasLivres +
				", poltronasTotal=" + poltronasTotal +
				", preco='" + preco + '\'' +
				", classe='" + classe + '\'' +
				", empresa='" + empresa + '\'' +
				", vende=" + vende +
				", km=" + km +
				", conexao="+conexao.toString()+
				'}';
	}
}

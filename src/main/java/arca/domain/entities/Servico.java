package arca.domain.entities;

import java.io.Serializable;

public class Servico implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
}

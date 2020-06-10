package arca.domain.entities;

import java.util.List;

public class Onibus {

	public Localidade origem;
	public Localidade destino;
	public String data;
	public String servico;
	public String grupo;
	public String empresa;
	public String saida;
	public String chegada;
	public String moeda;
	public String preco;
	public String linha;
	public List<Poltrona> mapaPoltrona;
}
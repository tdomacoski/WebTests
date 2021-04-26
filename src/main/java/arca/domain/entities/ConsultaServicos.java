package arca.domain.entities;

import java.util.List;

public class ConsultaServicos extends JsonModel {
	private static final long serialVersionUID = 1L;
	public Localidade origem;
	public Localidade destino;
	public String data;
	public List<Servico> lsServicos;
}

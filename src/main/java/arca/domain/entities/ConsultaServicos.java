package arca.domain.entities;

import java.io.Serializable;
import java.util.List;

public class ConsultaServicos implements Serializable {
	private static final long serialVersionUID = 1L;
	public Localidade origem;
	public Localidade destino;
	public String data;
	public List<Servico> lsServicos;
}

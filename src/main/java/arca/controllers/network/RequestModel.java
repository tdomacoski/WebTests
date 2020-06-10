package arca.controllers.network;

import arca.domain.entities.Conexao;
import arca.exceptions.NetworkException;

public interface RequestModel {
    String execute(final Conexao conexao, final String metodo, final String tipo) throws NetworkException;
}

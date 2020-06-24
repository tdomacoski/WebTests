package arca.controllers.network;

import arca.domain.entities.ConexaoOperadora;
import arca.exceptions.NetworkException;

public interface RequestModel {
    String execute(final ConexaoOperadora conexaoOperadora, final String metodo, final String tipo) throws NetworkException;
}

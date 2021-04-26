package arca.controllers.network;

import arca.domain.entities.ConexaoOperadora;
import arca.domain.entities.Error;
import arca.exceptions.NetworkException;

public interface RequestModel {
    ResponseModel execute(final ConexaoOperadora conexaoOperadora, final String metodo, final RequestType type) throws NetworkException;
    static class ResponseModel{
        public final String body;
        public final Error error;
        public ResponseModel(final String body){
            this.body = body;
            this.error = null;
        }
        public ResponseModel(final Error error){
            this.error = error;
            this.body = null;
        }
        public boolean isSucess(){
            return (body != null);
        }
    }

    static enum RequestType{
        GET,
        POST,
        PUT
    }
}

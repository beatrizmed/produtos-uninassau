package com.beatrizmed.dscatalago.services.exception;
//classe para o tratamento de exceção
public class DatabaseException extends RuntimeException {

    public DatabaseException(String msg) {
        super(msg);
    }
}

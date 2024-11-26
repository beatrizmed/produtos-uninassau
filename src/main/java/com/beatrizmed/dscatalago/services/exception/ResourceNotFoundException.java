package com.beatrizmed.dscatalago.services.exception;
//classe relacionada ao tratamento de exceção, usado para a busca dos dados
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}

package com.pedro.orso.softdesign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class VotoException {

    private static final String VOTO_IS_INVALID_ERROR = "Voto inválido.";
    private static final String VOTO_DOES_NOT_EXIST_ERROR = "O voto não existe.";
    private static final String VOTO_ALREADY_EXISTS_ERROR = "O voto já existe.";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class VotoIsInvalid extends BaseCustomException  {
        public VotoIsInvalid() {
            super(VOTO_IS_INVALID_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class VotoDoesNotExistException extends BaseCustomException  {
        public VotoDoesNotExistException() {
            super(VOTO_DOES_NOT_EXIST_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class VotoAlreadyExistsException extends BaseCustomException  {
        public VotoAlreadyExistsException() {
            super(VOTO_ALREADY_EXISTS_ERROR);
        }
    }
}
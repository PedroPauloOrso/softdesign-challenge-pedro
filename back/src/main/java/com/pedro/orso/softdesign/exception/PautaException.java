package com.pedro.orso.softdesign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PautaException {

    private static final String PAUTA_IS_INVALID_ERROR = "Pauta inválida.";
    private static final String PAUTA_DOES_NOT_EXIST_ERROR = "A pauta não existe.";
    private static final String PAUTA_ALREADY_CLOSED_ERROR = "Pauta já fechada.";
    private static final String PAUTA_ALREADY_STARTED_ERROR = "Pauta já iniciada";
    private static final String PAUTA_NOT_OPEN_ERROR = "A pauta não esta aberta";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class PautaIsInvalid extends BaseCustomException {
        public PautaIsInvalid() {
            super(PAUTA_IS_INVALID_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class PautaDoesNotExistException extends BaseCustomException {
        public PautaDoesNotExistException() {
            super(PAUTA_DOES_NOT_EXIST_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public static class PautaIsClosed extends BaseCustomException {
        public PautaIsClosed() {
            super(PAUTA_ALREADY_CLOSED_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public static class PautaIsStarted extends BaseCustomException {
        public PautaIsStarted() {
            super(PAUTA_ALREADY_STARTED_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public static class PautaIsNotOpen extends BaseCustomException {
        public PautaIsNotOpen() {
            super(PAUTA_NOT_OPEN_ERROR);
        }
    }
}

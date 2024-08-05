package com.pedro.orso.softdesign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AssociadoException {

    private static final String ASSOCIADO_IS_INVALID_ERROR = "Associado inválido.";
    private static final String ASSOCIADO_DOES_NOT_EXIST_ERROR = "Associado não existe.";
    private static final String ASSOCIADO_ALREADY_EXISTS_ERROR = "Associado com CPF já existe.";
    private static final String ASSOCIADO_CANT_VOTE_ERROR = "O Associado não pode votar.";


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class AssociadoIsInvalid extends BaseCustomException {
        public AssociadoIsInvalid() {
            super(ASSOCIADO_IS_INVALID_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class AssociadoDoesNotExistException extends BaseCustomException {
        public AssociadoDoesNotExistException() {
            super(ASSOCIADO_DOES_NOT_EXIST_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class AssociadoAlreadyExistException extends BaseCustomException {
        public AssociadoAlreadyExistException() {
            super(ASSOCIADO_ALREADY_EXISTS_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class AssociadoCantVoteException extends BaseCustomException {
        public AssociadoCantVoteException() {
            super(ASSOCIADO_CANT_VOTE_ERROR);
        }
    }
}

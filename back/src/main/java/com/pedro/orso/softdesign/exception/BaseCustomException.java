package com.pedro.orso.softdesign.exception;

public abstract class BaseCustomException extends RuntimeException {

    public BaseCustomException(String message) {
        super(message);
    }

}
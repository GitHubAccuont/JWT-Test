package org.justme.jwttest.data.exception;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String s) {
        super(s);
    }
}

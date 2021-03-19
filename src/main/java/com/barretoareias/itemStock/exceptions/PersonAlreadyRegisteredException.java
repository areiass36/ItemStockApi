package com.barretoareias.itemStock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonAlreadyRegisteredException extends Exception {

    public PersonAlreadyRegisteredException(String name) {
        super(String.format("Person %s is already registered in the system.",name));
    }
}

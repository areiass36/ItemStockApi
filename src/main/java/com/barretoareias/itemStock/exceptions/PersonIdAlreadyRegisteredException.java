package com.barretoareias.itemStock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonIdAlreadyRegisteredException extends Exception{

    public PersonIdAlreadyRegisteredException(Long id) {
        super(String.format("Person with id %s already created, It cannot have duplicates",id));
    }
}

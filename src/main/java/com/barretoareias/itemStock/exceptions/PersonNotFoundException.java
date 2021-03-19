package com.barretoareias.itemStock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends Exception{

    public PersonNotFoundException(String name) {
        super(String.format("Could not find person %s.",name));
    }

    public PersonNotFoundException(Long id){
        super(String.format("Could not find person with %s as id.",id));
    }
}

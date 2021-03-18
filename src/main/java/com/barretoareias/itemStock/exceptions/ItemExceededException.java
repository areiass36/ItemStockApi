package com.barretoareias.itemStock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemExceededException extends Exception{

    public ItemExceededException(Long id, int quantity){
        super(String.format("Could not add %s to id %s",quantity,id));
    }
}

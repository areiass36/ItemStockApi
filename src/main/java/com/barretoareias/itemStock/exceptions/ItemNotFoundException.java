package com.barretoareias.itemStock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends Exception{

    public ItemNotFoundException(String name){
        super(String.format("Could not find %s in system.",name));
    }

    public ItemNotFoundException(Long id){
        super(String.format("Could not find item id %s.",id));
    }
}

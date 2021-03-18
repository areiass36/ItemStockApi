package com.barretoareias.itemStock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFound extends Exception{

    public ItemNotFound(String name){
        super(String.format("Could not find %s in system.",name));
    }

    public ItemNotFound(Long id){
        super(String.format("Could not found item id %s.",id));
    }
}

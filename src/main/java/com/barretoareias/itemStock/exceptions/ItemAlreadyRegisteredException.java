package com.barretoareias.itemStock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemAlreadyRegisteredException extends Exception{

    public ItemAlreadyRegisteredException(String itemName){
        super(String.format("Item %s already registered in the system.", itemName));
    }

}

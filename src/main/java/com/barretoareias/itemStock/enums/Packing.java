package com.barretoareias.itemStock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Packing {

    LITERS("liters"),
    MILLILITERS("milliliters"),
    KILOS("kilos"),
    GRAMS("grams");


    private final String description;
}

package com.barretoareias.itemStock.builder;

import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.enums.Packing;
import lombok.Builder;

import java.sql.Date;
import java.util.Calendar;

@Builder
public class ItemDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Cheetos";

    @Builder.Default
    private String brand = "Elma Chips";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 8;

    @Builder.Default
    private Date addedDate = Date.valueOf("2021-01-01");

    @Builder.Default
    private Packing type = Packing.GRAMS;

    @Builder.Default
    private Long personId = 1l;

    public ItemDTO toItemDTO() {
        return new ItemDTO(id,
                name,
                brand,
                max,
                quantity,
                addedDate,
                type,
                personId);
    }
}

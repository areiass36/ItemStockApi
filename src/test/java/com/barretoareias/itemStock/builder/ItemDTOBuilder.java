package com.barretoareias.itemStock.builder;

import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.enums.Packing;
import lombok.Builder;

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
    private Packing type = Packing.GRAMS;

    public ItemDTO toItemDTO() {
        return new ItemDTO(id,
                name,
                brand,
                max,
                quantity,
                type);
    }
}

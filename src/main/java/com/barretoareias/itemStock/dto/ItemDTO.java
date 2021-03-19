package com.barretoareias.itemStock.dto;

import com.barretoareias.itemStock.enums.Packing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;

    @NotNull
    @Size(min=1, max=200)
    private String name;

    @NotNull
    @Size(min=1, max=200)
    private String brand;

    @NotNull
    @Max(500)
    private Integer max;

    @NotNull
    @Max(100)
    private Integer quantity;

    @NotNull
    private Date addedDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Packing type;

    @NotNull
    private Long personId;
}

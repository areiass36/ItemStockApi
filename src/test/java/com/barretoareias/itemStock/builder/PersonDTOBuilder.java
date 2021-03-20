package com.barretoareias.itemStock.builder;

import com.barretoareias.itemStock.dto.PersonDTO;
import lombok.Builder;

@Builder
public class PersonDTOBuilder {

    @Builder.Default
    private Long id = 0l;

    @Builder.Default
    private String name = "Augusto";

    public PersonDTO toPersonDTO(){
        return new PersonDTO(id,name);
    }
}

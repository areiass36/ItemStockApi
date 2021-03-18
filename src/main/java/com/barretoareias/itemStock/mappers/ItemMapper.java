package com.barretoareias.itemStock.mappers;

import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toModel(ItemDTO itemDTO);

    ItemDTO toDTO(Item item);
}

package com.barretoareias.itemStock.mappers;

import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.dto.ItemDTO.ItemDTOBuilder;
import com.barretoareias.itemStock.entity.Item;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-18T06:58:47-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item toModel(ItemDTO itemDTO) {
        if ( itemDTO == null ) {
            return null;
        }

        Item item = new Item();

        item.setId( itemDTO.getId() );
        item.setName( itemDTO.getName() );
        item.setBrand( itemDTO.getBrand() );
        if ( itemDTO.getMax() != null ) {
            item.setMax( itemDTO.getMax() );
        }
        if ( itemDTO.getQuantity() != null ) {
            item.setQuantity( itemDTO.getQuantity() );
        }
        item.setType( itemDTO.getType() );

        return item;
    }

    @Override
    public ItemDTO toDTO(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemDTOBuilder itemDTO = ItemDTO.builder();

        itemDTO.id( item.getId() );
        itemDTO.name( item.getName() );
        itemDTO.brand( item.getBrand() );
        itemDTO.max( item.getMax() );
        itemDTO.quantity( item.getQuantity() );
        itemDTO.type( item.getType() );

        return itemDTO.build();
    }
}

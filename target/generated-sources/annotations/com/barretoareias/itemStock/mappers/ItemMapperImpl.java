package com.barretoareias.itemStock.mappers;

import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.entity.Item;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-19T19:29:01-0300",
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
        item.setAddedDate( itemDTO.getAddedDate() );
        item.setType( itemDTO.getType() );
        item.setPersonId( itemDTO.getPersonId() );

        return item;
    }

    @Override
    public ItemDTO toDTO(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId( item.getId() );
        itemDTO.setName( item.getName() );
        itemDTO.setBrand( item.getBrand() );
        itemDTO.setMax( item.getMax() );
        itemDTO.setQuantity( item.getQuantity() );
        itemDTO.setAddedDate( item.getAddedDate() );
        itemDTO.setType( item.getType() );
        itemDTO.setPersonId( item.getPersonId() );

        return itemDTO;
    }
}

package com.barretoareias.itemStock.service;

import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.entity.Item;
import com.barretoareias.itemStock.exceptions.ItemAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.ItemExceededException;
import com.barretoareias.itemStock.exceptions.ItemNotFound;
import com.barretoareias.itemStock.mappers.ItemMapper;
import com.barretoareias.itemStock.repository.ItemRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;

    public ItemDTO createItem(ItemDTO itemDTO) throws ItemAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(itemDTO.getName());
        Item item = itemMapper.toModel(itemDTO);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toDTO(savedItem);
    }

    public ItemDTO findByName(String name) throws ItemNotFound {
        Item foundItem = itemRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFound(name));
        return itemMapper.toDTO(foundItem);
    }

    public List<ItemDTO> listAll(){
        return itemRepository.findAll().stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws ItemNotFound {
        verifyIfExists(id);
        itemRepository.deleteById(id);
    }

    private Item verifyIfExists(Long id) throws ItemNotFound {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFound(id));
    }

    private void verifyIfIsAlreadyRegistered(String name) throws ItemAlreadyRegisteredException{
        Optional<Item> optSavedItem = itemRepository.findByName(name);
        if(optSavedItem.isPresent()){
            throw new ItemAlreadyRegisteredException(name);
        }
    }

    public ItemDTO incrementQuantity(Long id, int quantityToIncrement) throws ItemNotFound, ItemExceededException {
        Item itemToIncrement = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + itemToIncrement.getQuantity();
        if(quantityAfterIncrement <= itemToIncrement.getMax()) {
            itemToIncrement.setQuantity(quantityAfterIncrement);
            Item incrementedItem = itemRepository.save(itemToIncrement);
            return itemMapper.toDTO(incrementedItem);
        }
        throw new ItemExceededException(id,quantityToIncrement);
    }
}

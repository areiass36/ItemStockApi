package com.barretoareias.itemStock.controller;

import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.dto.QuantityDTO;
import com.barretoareias.itemStock.entity.Item;
import com.barretoareias.itemStock.exceptions.ItemAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.ItemExceededException;
import com.barretoareias.itemStock.exceptions.ItemNotFoundException;
import com.barretoareias.itemStock.exceptions.PersonNotFoundException;
import com.barretoareias.itemStock.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/item")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController implements ItemControllerDocs{

    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO createItem(@RequestBody @Valid ItemDTO itemDTO) throws ItemAlreadyRegisteredException, PersonNotFoundException {
        return itemService.createItem(itemDTO);
    }

    @GetMapping("/{name}")
    public ItemDTO findByName(@PathVariable String name) throws ItemNotFoundException {
        return itemService.findByName(name);
    }

    @PostMapping("/teste")
    public Item createTeste(@PathVariable Item item){
        return item;
    }

    @GetMapping
    public List<ItemDTO> listItems() {
        return itemService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(Long id) throws ItemNotFoundException {
        itemService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public ItemDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws ItemNotFoundException, ItemExceededException{
        return itemService.incrementQuantity(id,quantityDTO.getQuantity());
    }

}

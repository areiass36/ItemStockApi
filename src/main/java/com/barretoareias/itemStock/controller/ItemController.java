package com.barretoareias.itemStock.controller;

import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.dto.QuantityDTO;
import com.barretoareias.itemStock.exceptions.ItemAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.ItemExceededException;
import com.barretoareias.itemStock.exceptions.ItemNotFound;
import com.barretoareias.itemStock.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v001/item")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController implements ItemControllerDocs{

    private final ItemService itemService;

    /*
    @GetMapping
    public String test(){
        return "TEste";
    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO createItem(@RequestBody @Valid ItemDTO itemDTO) throws ItemAlreadyRegisteredException {
        return itemService.createItem(itemDTO);
    }

    @GetMapping("/{name}")
    public ItemDTO findByName(@PathVariable String name) throws ItemNotFound {
        return itemService.findByName(name);
    }

    @GetMapping
    public List<ItemDTO> listItems() {
        return itemService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(Long id) throws ItemNotFound {
        itemService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public ItemDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws ItemNotFound, ItemExceededException{
        return itemService.incrementQuantity(id,quantityDTO.getQuantity());
    }

}

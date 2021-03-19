package com.barretoareias.itemStock.controller;

import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.exceptions.ItemAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.ItemNotFoundException;
import com.barretoareias.itemStock.exceptions.PersonNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api("Manages item stock")
public interface ItemControllerDocs {

    @ApiOperation(value = "Item creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success item creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    ItemDTO createItem (ItemDTO itemDTO) throws ItemAlreadyRegisteredException, PersonNotFoundException;

    @ApiOperation(value = "Returns item found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success item found in the system"),
            @ApiResponse(code = 404, message = "Item with given name not found.")
    })
    ItemDTO findByName(@PathVariable String name) throws ItemNotFoundException;

    @ApiOperation(value = "Returns a list of all items registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all items registered in the system"),
    })
    List<ItemDTO> listItems();

    @ApiOperation(value = "Delete an item found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success item deleted in the system"),
            @ApiResponse(code = 404, message = "Item with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws ItemNotFoundException;
}

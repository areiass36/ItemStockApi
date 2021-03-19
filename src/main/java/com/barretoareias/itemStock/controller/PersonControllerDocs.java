package com.barretoareias.itemStock.controller;

import com.barretoareias.itemStock.dto.PersonDTO;
import com.barretoareias.itemStock.entity.Person;
import com.barretoareias.itemStock.exceptions.PersonAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.PersonIdAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.PersonNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api("Manages Person Controller")
public interface PersonControllerDocs {

    @ApiOperation(value = "Creates a person")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Person was created."),
            @ApiResponse(code = 400, message = "Missing required fields to create a person.")
    })
    PersonDTO createPerson(PersonDTO personDTO) throws PersonAlreadyRegisteredException, PersonIdAlreadyRegisteredException;

    @ApiOperation(value = "Find Person by name.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Person was found."),
            @ApiResponse(code = 404, message = "Could not find person in system.")
    })
    PersonDTO findPersonByName(@PathVariable String name) throws PersonNotFoundException;

    @ApiOperation(value = "Deletes a person by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Person deleted."),
            @ApiResponse(code = 404, message = "Person not found.")
    })
    void deletePersonById(@PathVariable Long id) throws PersonNotFoundException;

    @ApiOperation(value = "List person")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of person.")
    })
    List<PersonDTO> listPerson();

    @ApiOperation(value = "Update Person")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Update was a success"),
            @ApiResponse(code = 404, message = "Person not found")
    })
    PersonDTO updatePerson(@RequestBody PersonDTO person) throws PersonNotFoundException;

}

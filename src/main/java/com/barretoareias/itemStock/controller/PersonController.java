package com.barretoareias.itemStock.controller;

import com.barretoareias.itemStock.dto.PersonDTO;
import com.barretoareias.itemStock.entity.Person;
import com.barretoareias.itemStock.exceptions.PersonAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.PersonIdAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.PersonNotFoundException;
import com.barretoareias.itemStock.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/person")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController implements PersonControllerDocs{

    private PersonService personService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO createPerson(@RequestBody @Valid PersonDTO personDTO) throws PersonAlreadyRegisteredException, PersonIdAlreadyRegisteredException {
        return personService.createPerson(personDTO);
    }

    @Override
    @GetMapping("/{name}")
    public PersonDTO findPersonByName(@PathVariable String name) throws PersonNotFoundException {
        return personService.findByName(name);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonById(@PathVariable Long id) throws PersonNotFoundException {
        personService.deletePersonById(id);
    }

    @Override
    @GetMapping
    public List<PersonDTO> listPerson() {
        return personService.listAll();
    }

    @Override
    @PutMapping
    public PersonDTO updatePerson(PersonDTO person) throws PersonNotFoundException {
        return personService.updatePerson(person);
    }
}

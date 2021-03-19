package com.barretoareias.itemStock.service;

import com.barretoareias.itemStock.dto.PersonDTO;
import com.barretoareias.itemStock.entity.Person;
import com.barretoareias.itemStock.exceptions.PersonAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.PersonNotFoundException;
import com.barretoareias.itemStock.mappers.PersonMapper;
import com.barretoareias.itemStock.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public PersonDTO createPerson(PersonDTO personDTO) throws PersonAlreadyRegisteredException {
        verifyIfPersonExists(personDTO);
        Person personToAdd = personMapper.toPerson(personDTO);
        Person personSaved = personRepository.save(personToAdd);
        return personMapper.toDTO(personSaved);
    }

    public void deletePersonById(Long id) throws PersonNotFoundException {
        Person personFound = verifyIfPersonExistsById(id);
        personRepository.delete(personFound);
    }

    public List<PersonDTO> listAll(){
        return personRepository.findAll().stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findByName(String name) throws PersonNotFoundException {
        return verifyIfPersonExistsByName(name);
    }

    private PersonDTO verifyIfPersonExistsByName(String name) throws PersonNotFoundException{
        return personMapper.toDTO(personRepository.findByName(name).orElseThrow(
                () -> new PersonNotFoundException(name)
        ));
    }

    private Person verifyIfPersonExistsById(Long id) throws PersonNotFoundException{
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    private void verifyIfPersonExists(PersonDTO personDTO) throws PersonAlreadyRegisteredException{
        Person person = personMapper.toPerson(personDTO);
        Optional<Person> foundPerson = personRepository.findByName(person.getName());
        if(foundPerson.isEmpty()){
            throw new PersonAlreadyRegisteredException(person.getName());
        }
    }
}

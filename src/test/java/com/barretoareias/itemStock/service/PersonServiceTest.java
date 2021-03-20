package com.barretoareias.itemStock.service;

import com.barretoareias.itemStock.builder.PersonDTOBuilder;
import com.barretoareias.itemStock.dto.PersonDTO;
import com.barretoareias.itemStock.entity.Item;
import com.barretoareias.itemStock.exceptions.PersonAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.PersonIdAlreadyRegisteredException;
import com.barretoareias.itemStock.mappers.PersonMapper;
import com.barretoareias.itemStock.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    private PersonMapper personMapper = PersonMapper.INSTANCE;

    @InjectMocks
    private PersonService personService;


    @Test
    void whenGivenRightValueItShouldRegister() throws PersonIdAlreadyRegisteredException, PersonAlreadyRegisteredException {
        var person = PersonDTOBuilder.builder().build().toPersonDTO();
        var expectedPerson = personMapper.toPerson(person);

        when(personRepository.findByName(person.getName())).thenReturn(Optional.empty());
        when(personRepository.save(expectedPerson)).thenReturn(expectedPerson);

        var personReturned = personService.createPerson(person);

        assertThat(personReturned.getName(),is(equalTo(person.getName())));
    }

    @Test
    void whenPersonAlreadyRegisteredItShouldReturnAnException() throws PersonIdAlreadyRegisteredException, PersonAlreadyRegisteredException {
        var person = PersonDTOBuilder.builder().build().toPersonDTO();
        var duplicatedPerson = personMapper.toPerson(person);

        when(personRepository.findByName(person.getName())).thenReturn(Optional.of(duplicatedPerson));

        assertThrows(PersonAlreadyRegisteredException.class, () -> personService.createPerson(person));
    }


}

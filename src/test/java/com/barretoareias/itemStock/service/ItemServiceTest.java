package com.barretoareias.itemStock.service;

import com.barretoareias.itemStock.builder.ItemDTOBuilder;
import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.entity.Item;
import com.barretoareias.itemStock.exceptions.ItemAlreadyRegisteredException;
import com.barretoareias.itemStock.exceptions.ItemExceededException;
import com.barretoareias.itemStock.exceptions.ItemNotFoundException;
import com.barretoareias.itemStock.exceptions.PersonNotFoundException;
import com.barretoareias.itemStock.mappers.ItemMapper;
import com.barretoareias.itemStock.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private ItemRepository itemRepository;

    private ItemMapper itemMapper = ItemMapper.INSTANCE;

    @InjectMocks
    private ItemService itemService;

    @Test
    void whenItemInformedThenItShouldBeCreated() throws ItemAlreadyRegisteredException, PersonNotFoundException {
        // given
        ItemDTO expectedItemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        Item expectedSavedItem = itemMapper.toModel(expectedItemDTO);

        // when
        when(itemRepository.findByName(expectedItemDTO.getName())).thenReturn(Optional.empty());
        when(itemRepository.save(expectedSavedItem)).thenReturn(expectedSavedItem);

        //then
        ItemDTO createdItemDTO = itemService.createItem(expectedItemDTO);

        assertThat(createdItemDTO.getId(), is(equalTo(expectedItemDTO.getId())));
        assertThat(createdItemDTO.getName(), is(equalTo(expectedItemDTO.getName())));
        assertThat(createdItemDTO.getQuantity(), is(equalTo(expectedItemDTO.getQuantity())));
    }

    @Test
    void whenAlreadyRegisteredItemInformedThenAnExceptionShouldBeThrown() {
        // given
        ItemDTO expectedItemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        Item duplicatedItem = itemMapper.toModel(expectedItemDTO);

        // when
        when(itemRepository.findByName(expectedItemDTO.getName())).thenReturn(Optional.of(duplicatedItem));

        // then
        assertThrows(ItemAlreadyRegisteredException.class, () -> itemService.createItem(expectedItemDTO));
    }

    @Test
    void whenValidItemNameIsGivenThenReturnAItem() throws ItemNotFoundException {
        // given
        ItemDTO expectedFoundItemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        Item expectedFoundItem = itemMapper.toModel(expectedFoundItemDTO);

        // when
        when(itemRepository.findByName(expectedFoundItem.getName())).thenReturn(Optional.of(expectedFoundItem));

        // then
        ItemDTO foundItemDTO = itemService.findByName(expectedFoundItemDTO.getName());

        assertThat(foundItemDTO, is(equalTo(expectedFoundItemDTO)));
    }

    @Test
    void whenNotRegisteredItemNameIsGivenThenThrowAnException() {
        // given
        ItemDTO expectedFoundItemDTO = ItemDTOBuilder.builder().build().toItemDTO();

        // when
        when(itemRepository.findByName(expectedFoundItemDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(ItemNotFoundException.class, () -> itemService.findByName(expectedFoundItemDTO.getName()));
    }

    @Test
    void whenListItemIsCalledThenReturnAListOfItems() {
        // given
        ItemDTO expectedFoundItemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        Item expectedFoundItem = itemMapper.toModel(expectedFoundItemDTO);

        //when
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundItem));

        //then
        List<ItemDTO> foundListItemsDTO = itemService.listAll();

        assertThat(foundListItemsDTO, is(not(empty())));
        assertThat(foundListItemsDTO.get(0), is(equalTo(expectedFoundItemDTO)));
    }

    @Test
    void whenListItemIsCalledThenReturnAnEmptyListOfItems() {
        //when
        when(itemRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<ItemDTO> foundListItemsDTO = itemService.listAll();

        assertThat(foundListItemsDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAItemShouldBeDeleted() throws ItemNotFoundException {
        // given
        ItemDTO expectedDeletedItemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        Item expectedDeletedItem = itemMapper.toModel(expectedDeletedItemDTO);

        // when
        when(itemRepository.findById(expectedDeletedItemDTO.getId())).thenReturn(Optional.of(expectedDeletedItem));
        doNothing().when(itemRepository).deleteById(expectedDeletedItemDTO.getId());

        // then
        itemService.deleteById(expectedDeletedItemDTO.getId());

        verify(itemRepository, times(1)).findById(expectedDeletedItemDTO.getId());
        verify(itemRepository, times(1)).deleteById(expectedDeletedItemDTO.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrementItemStock() throws ItemNotFoundException, ItemExceededException {
        //given
        ItemDTO expectedItemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        Item expectedItem = itemMapper.toModel(expectedItemDTO);

        //when
        when(itemRepository.findById(expectedItemDTO.getId())).thenReturn(Optional.of(expectedItem));
        when(itemRepository.save(expectedItem)).thenReturn(expectedItem);

        int quantityToIncrement = 5;
        int expectedQuantityAfterIncrement = expectedItemDTO.getQuantity() + quantityToIncrement;

        // then
        ItemDTO incrementedItemDTO = itemService.incrementQuantity(expectedItemDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedItemDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedItemDTO.getMax()));
    }

    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException() {
        ItemDTO expectedItemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        Item expectedItem = itemMapper.toModel(expectedItemDTO);

        when(itemRepository.findById(expectedItemDTO.getId())).thenReturn(Optional.of(expectedItem));

        int quantityToIncrement = 80;
        assertThrows(ItemExceededException.class, () -> itemService.incrementQuantity(expectedItemDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementAfterSumIsGreaterThanMaxThenThrowException() {
        ItemDTO expectedItemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        Item expectedItem = itemMapper.toModel(expectedItemDTO);

        when(itemRepository.findById(expectedItemDTO.getId())).thenReturn(Optional.of(expectedItem));

        int quantityToIncrement = 45;
        assertThrows(ItemExceededException.class, () -> itemService.incrementQuantity(expectedItemDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToIncrement = 10;

        when(itemRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.incrementQuantity(INVALID_BEER_ID, quantityToIncrement));
    }
}
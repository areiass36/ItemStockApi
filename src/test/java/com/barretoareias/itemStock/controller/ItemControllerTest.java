package com.barretoareias.itemStock.controller;
import com.barretoareias.itemStock.builder.ItemDTOBuilder;
import com.barretoareias.itemStock.dto.ItemDTO;
import com.barretoareias.itemStock.dto.QuantityDTO;
import com.barretoareias.itemStock.exceptions.ItemNotFound;
import com.barretoareias.itemStock.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.barretoareias.itemStock.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    private static final String ITEM_API_URL_PATH = "/api/v1/items";
    private static final long VALID_ITEM_ID = 1L;
    private static final long INVALID_ITEM_ID = 2l;
    private static final String ITEM_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String ITEM_API_SUBPATH_DECREMENT_URL = "/decrement";

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAItemIsCreated() throws Exception {
        // given
        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();

        // when
        when(itemService.createItem(itemDTO)).thenReturn(itemDTO);

        // then
        mockMvc.perform(post(ITEM_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(itemDTO.getName())))
                .andExpect(jsonPath("$.brand", is(itemDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(itemDTO.getType().toString())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        itemDTO.setBrand(null);

        // then
        mockMvc.perform(post(ITEM_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();

        //when
        when(itemService.findByName(itemDTO.getName())).thenReturn(itemDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ITEM_API_URL_PATH + "/" + itemDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(itemDTO.getName())))
                .andExpect(jsonPath("$.brand", is(itemDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(itemDTO.getType().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        // given
        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();

        //when
        when(itemService.findByName(itemDTO.getName())).thenThrow(ItemNotFound.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ITEM_API_URL_PATH + "/" + itemDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithItemsIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();

        //when
        when(itemService.listAll()).thenReturn(Collections.singletonList(itemDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ITEM_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(itemDTO.getName())))
                .andExpect(jsonPath("$[0].brand", is(itemDTO.getBrand())))
                .andExpect(jsonPath("$[0].type", is(itemDTO.getType().toString())));
    }

    @Test
    void whenGETListWithoutItemsIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();

        //when
        when(itemService.listAll()).thenReturn(Collections.singletonList(itemDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ITEM_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();

        //when
        doNothing().when(itemService).deleteById(itemDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(ITEM_API_URL_PATH + "/" + itemDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(ItemNotFound.class).when(itemService).deleteById(INVALID_ITEM_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(ITEM_API_URL_PATH + "/" + INVALID_ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() throws Exception {
        QuantityDTO quantityDTO = QuantityDTO.builder()
                .quantity(10)
                .build();

        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();
        itemDTO.setQuantity(itemDTO.getQuantity() + quantityDTO.getQuantity());

        when(itemService.incrementQuantity(VALID_ITEM_ID, quantityDTO.getQuantity())).thenReturn(itemDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch(ITEM_API_URL_PATH + "/" + VALID_ITEM_ID + ITEM_API_SUBPATH_INCREMENT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(itemDTO.getName())))
                .andExpect(jsonPath("$.brand", is(itemDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(itemDTO.getType().toString())))
                .andExpect(jsonPath("$.quantity", is(itemDTO.getQuantity())));
    }

//    @Test
//    void whenPATCHIsCalledToIncrementGreatherThanMaxThenBadRequestStatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(30)
//                .build();
//
//        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();
//        itemDTO.setQuantity(itemDTO.getQuantity() + quantityDTO.getQuantity());
//
//        when(itemService.increment(VALID_ITEM_ID, quantityDTO.getQuantity())).thenThrow(ItemStockExceededException.class);
//
//        mockMvc.perform(patch(ITEM_API_URL_PATH + "/" + VALID_ITEM_ID + ITEM_API_SUBPATH_INCREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .con(asJsonString(quantityDTO))).andExpect(status().isBadRequest());
//    }

//    @Test
//    void whenPATCHIsCalledWithInvalidItemIdToIncrementThenNotFoundStatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(30)
//                .build();
//
//        when(itemService.increment(INVALID_ITEM_ID, quantityDTO.getQuantity())).thenThrow(ItemNotFoundException.class);
//        mockMvc.perform(patch(ITEM_API_URL_PATH + "/" + INVALID_ITEM_ID + ITEM_API_SUBPATH_INCREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(quantityDTO)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void whenPATCHIsCalledToDecrementDiscountThenOKstatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(5)
//                .build();
//
//        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();
//        itemDTO.setQuantity(itemDTO.getQuantity() + quantityDTO.getQuantity());
//
//        when(itemService.decrement(VALID_ITEM_ID, quantityDTO.getQuantity())).thenReturn(itemDTO);
//
//        mockMvc.perform(patch(ITEM_API_URL_PATH + "/" + VALID_ITEM_ID + ITEM_API_SUBPATH_DECREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(quantityDTO))).andExpect(status().isOk())
//                .andExpect(jsonPath("$.name", is(itemDTO.getName())))
//                .andExpect(jsonPath("$.brand", is(itemDTO.getBrand())))
//                .andExpect(jsonPath("$.type", is(itemDTO.getType().toString())))
//                .andExpect(jsonPath("$.quantity", is(itemDTO.getQuantity())));
//    }
//
//    @Test
//    void whenPATCHIsCalledToDEcrementLowerThanZeroThenBadRequestStatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(60)
//                .build();
//
//        ItemDTO itemDTO = ItemDTOBuilder.builder().build().toItemDTO();
//        itemDTO.setQuantity(itemDTO.getQuantity() + quantityDTO.getQuantity());
//
//        when(itemService.decrement(VALID_ITEM_ID, quantityDTO.getQuantity())).thenThrow(ItemStockExceededException.class);
//
//        mockMvc.perform(patch(ITEM_API_URL_PATH + "/" + VALID_ITEM_ID + ITEM_API_SUBPATH_DECREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(quantityDTO))).andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void whenPATCHIsCalledWithInvalidItemIdToDecrementThenNotFoundStatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(5)
//                .build();
//
//        when(itemService.decrement(INVALID_ITEM_ID, quantityDTO.getQuantity())).thenThrow(ItemNotFoundException.class);
//        mockMvc.perform(patch(ITEM_API_URL_PATH + "/" + INVALID_ITEM_ID + ITEM_API_SUBPATH_DECREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(quantityDTO)))
//                .andExpect(status().isNotFound());
//    }
}
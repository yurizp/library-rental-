package com.agriness.libraryrental.controller;

import com.agriness.libraryrental.dto.BookSummaryDto;
import com.agriness.libraryrental.dto.RentalBookSummaryDto;
import com.agriness.libraryrental.dto.TaxDto;
import com.agriness.libraryrental.service.RentalBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import utils.ResourceUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = ClientController.class)
@WithMockUser(username = "user", roles = {"user", "library-rental"})
class ClientControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private RentalBookService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        client = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldReturnAllReservedBooks() throws Exception {
        String response = ResourceUtils.loadResourceAsString("json/client/return-reserved-books.json");
        when(service.getRentalBooksByClientId(anyLong())).thenReturn(Arrays.asList(createRentalBookSummaryDto()));
        client.perform(get("/v1/clients/123/books/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(response));
    }

    private RentalBookSummaryDto createRentalBookSummaryDto() {
        return RentalBookSummaryDto.builder()
                .tax(createTaxDto())
                .bookSummary(createBookSummaryDto())
                .renteDate(LocalDate.of(2020, 12, 3))
                .build();
    }

    private BookSummaryDto createBookSummaryDto() {
        return BookSummaryDto.builder()
                .id(42L)
                .description("Description")
                .isbn10("isbn10")
                .isbn13("isbn13")
                .title("title")
                .properties(Collections.EMPTY_LIST)
                .build();
    }

    private TaxDto createTaxDto() {
        return TaxDto.builder()
                .dailyRate(0.6D)
                .daysArrear(7)
                .description("description")
                .penalty(7D)
                .totalTax(13D)
                .build();
    }

}
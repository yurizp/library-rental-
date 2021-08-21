package com.agriness.libraryrental.controller;

import com.agriness.libraryrental.dto.BookDto;
import com.agriness.libraryrental.dto.BookPropertyDto;
import com.agriness.libraryrental.dto.RentalBookDto;
import com.agriness.libraryrental.enums.StatusEnum;
import com.agriness.libraryrental.service.BookService;
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

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = BookController.class)
@WithMockUser(username = "user", roles = {"user", "library-rental"})
class BookControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private BookService service;

    @MockBean
    private RentalBookService rentalBookService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        client = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldReturnAllBooks() throws Exception {
        String response = ResourceUtils.loadResourceAsString("json/book/get-all-books.json");
        when(service.getAllBook()).thenReturn(Arrays.asList(createBookDto()));
        client.perform(get("/v1/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(response));
    }

    @Test
    void shouldReturUnauthorizedWhenNotSendTokenToGetAllBooks() throws Exception {
        String response = ResourceUtils.loadResourceAsString("json/book/get-all-books.json");
        when(service.getAllBook()).thenReturn(Arrays.asList(createBookDto()));
        client.perform(get("/v1/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(response));
    }

    @Test
    void shouldReserve() throws Exception {
        client.perform(post("/v1/books/123/reserve/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clientId\":333}"))
                .andExpect(status().is(202));
        verify(rentalBookService).rentBook(eq(123L), eq(333L));
    }

    @Test
    void shouldReturnBook() throws Exception {
        client.perform(post("/v1/books/123/return/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clientId\":333}"))
                .andExpect(status().is(202));
        verify(rentalBookService).returnRentedBook(eq(123L), eq(333L));
    }

    private BookDto createBookDto() {
        return BookDto.builder()
                .id(483L)
                .title("title")
                .status(StatusEnum.AVAILABLE)
                .rentalBooks(Arrays.asList(createRentalBookDto()))
                .isbn13("isbn13")
                .isbn10("isbn10")
                .description("description")
                .properties(Arrays.asList(createBookPropertyDto()))
                .build();
    }

    private BookPropertyDto createBookPropertyDto() {
        return BookPropertyDto.builder()
                .value("value")
                .key("key")
                .id(189L)
                .build();
    }

    private RentalBookDto createRentalBookDto() {
        return RentalBookDto.builder()
                .status(StatusEnum.AVAILABLE)
                .id(1L)
                .build();
    }
}
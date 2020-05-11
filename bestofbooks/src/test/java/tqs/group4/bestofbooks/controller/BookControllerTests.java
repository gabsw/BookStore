package tqs.group4.bestofbooks.controller;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.service.BookService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@WebMvcTest(BooksController.class)
public class BookControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @AfterEach
    public void after() {
        reset(bookService);
    }

    @Test
    void givenExistentIsbn_whenGetBookByIsbn_thenReturnJson() throws Exception {
        String existentIsbn = BookMocks.onTheRoad.getIsbn();
        String url = "/api/books/isbn/" + existentIsbn;
        given(bookService.getBookByIsbn(existentIsbn)).willReturn(BookMocks.onTheRoad);

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(BookMocks.onTheRoad)));
        verify(bookService, VerificationModeFactory.times(1)).getBookByIsbn(existentIsbn);
    }

    @Test
    void givenInvalidIsbn_thenThrowHTTPStatusNotFound() throws Exception {
        String invalidIsbn = "123";
        String url = "/api/books/isbn/" + invalidIsbn;

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenBookNotFoundException_whenGetBookByIsbn_thenThrowHTTPStatusNotFound() throws Exception {
        String unknownIsbn = "9780140042543";
        String url = "/api/books/isbn/" + unknownIsbn;
        given(bookService.getBookByIsbn(unknownIsbn)).willThrow(new BookNotFoundException());

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        )
           .andExpect(status().isNotFound());
        verify(bookService, VerificationModeFactory.times(1)).getBookByIsbn(unknownIsbn);
    }

    @Test
    void givenPageOfBooks_whenGetAvailableBooks_thenReturnJson() throws Exception {
        Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.infiniteJest, BookMocks.onTheRoad), p, 2);

        given(bookService.getAvailableBooks(p)).willReturn(bookPage);

        mvc.perform(get("/api/books/available").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
           .andExpect(content().json(toJson(bookPage)));
        verify(bookService, VerificationModeFactory.times(1)).getAvailableBooks(p);
    }

    @Test
    void givenPageOfBooks_whenSearchingForBooks_thenReturnJson() throws Exception {
        Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.onTheRoad), p, 1);

        given(bookService.getFilteredBooks(BookMocks.onTheRoad.getTitle(), BookMocks.onTheRoad.getAuthor(),
                BookMocks.onTheRoad.getCategory(), p))
                .willReturn(bookPage);

        mvc.perform(get("/api/books/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", BookMocks.onTheRoad.getTitle())
                .param("category", BookMocks.onTheRoad.getCategory())
                .param("author", BookMocks.onTheRoad.getAuthor()))
           .andExpect(status().isOk())
           .andExpect(content().json(toJson(bookPage)));
        verify(bookService, VerificationModeFactory.times(1)).getFilteredBooks(
                BookMocks.onTheRoad.getTitle(), BookMocks.onTheRoad.getAuthor(),
                BookMocks.onTheRoad.getCategory(), p);
    }
}

package tqs.group4.bestofbooks.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.utils.Json.toJson;

import javax.servlet.http.HttpServletRequest;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;

import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.service.BookService;
import tqs.group4.bestofbooks.service.LoginServices;
import tqs.group4.bestofbooks.service.StockService;

@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {

	 @Autowired
	 private MockMvc mvc;

	 @MockBean
	 private StockService stockService;
	 
	 @MockBean
	 private LoginServices loginService;
	 
	 
	 @AfterEach
	 public void after() {
	     reset(stockService);
	     reset(loginService);
	 }
	 
	 @Test
	 void givenValidPublisherToken_whenGetAvailableStock_thenReturnJsonWithBooks() throws JsonProcessingException, Exception {
		 UserDto dto = new UserDto("username", "Publisher");
		 dto.addAttribute("name", "PublisherName");
		 dto.addAttribute("tin", "PublisherTIN");
		 Book b1 = new Book("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5,
	                "Travelogue", "Publisher");
		 Book b2 = new Book("9876543216842", "Title 2", "Author 2", "Description 2", 15, 3,
	                "Travelogue", "Publisher");
		 Pageable p = PageRequest.of(0, 20);
	     Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(b1, b2), p, 2);
		 given(loginService.getSessionUsername(any(HttpServletRequest.class))).willReturn("username");
		 given(loginService.getUserDtoByUsername("username")).willReturn(dto);
		 given(stockService.getPublisherBooks("PublisherName", p)).willReturn(bookPage);
		 
		 String url = "/api/publisher/stock/";
		 
		 mvc.perform(get(url)
	                .contentType(MediaType.APPLICATION_JSON)
	        ).andExpect(status()
	                .isOk())
	           .andExpect(content().json(toJson(bookPage)));
		 
		 verify(loginService, VerificationModeFactory.times(1)).getSessionUsername(any(HttpServletRequest.class));
		 verify(loginService, VerificationModeFactory.times(1)).getUserDtoByUsername("username");
		 verify(stockService, VerificationModeFactory.times(1)).getPublisherBooks("PublisherName", p);
	 }
	 
	 @Test
	 void givenInvalidPublisherToken_whenGetAvailableStock_thenStatus500() throws JsonProcessingException, Exception {
		 UserDto dto = new UserDto("username", "Publisher");
		 dto.addAttribute("name", "PublisherName");
		 dto.addAttribute("tin", "PublisherTIN");
		 Book b1 = new Book("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5,
	                "Travelogue", "Publisher");
		 Book b2 = new Book("9876543216842", "Title 2", "Author 2", "Description 2", 15, 3,
	                "Travelogue", "Publisher");
		 Pageable p = PageRequest.of(0, 20);
	     Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(b1, b2), p, 2);
		 given(loginService.getSessionUsername(any(HttpServletRequest.class))).willReturn(null);
		 given(loginService.getUserDtoByUsername(null)).willThrow(new IllegalArgumentException("Username is not defined."));
		 		 
		 String url = "/api/publisher/stock/";
		 
		 mvc.perform(get(url)
	                .contentType(MediaType.APPLICATION_JSON)
	        ).andExpect(status()
	                .isInternalServerError());
		 
		 verify(loginService, VerificationModeFactory.times(1)).getSessionUsername(any(HttpServletRequest.class));
		 verify(loginService, VerificationModeFactory.times(1)).getUserDtoByUsername(null);
	 }
	 
}

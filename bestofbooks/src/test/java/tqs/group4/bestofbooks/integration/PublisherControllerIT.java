package tqs.group4.bestofbooks.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.utils.Json.toJson;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

import tqs.group4.bestofbooks.BestofbooksApplication;
import tqs.group4.bestofbooks.dto.StockDto;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.model.Buyer;
import tqs.group4.bestofbooks.model.Publisher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
classes = BestofbooksApplication.class)
@AutoConfigureMockMvc
@Transactional
public class PublisherControllerIT {
	
	@Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;

	@BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, publishers").executeUpdate();
        Publisher viking = new Publisher("viking", "30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4", "Viking Press", "tin4");
        entityManager.persist(viking);
        entityManager.flush();
    }
	
	@Test
	void givenSuccessfulLoginAndMatchingName_whenGetAvailableStock_thenReturnJson() throws JsonProcessingException, Exception {
		entityManager.persist(BookMocks.onTheRoad);
        entityManager.flush();
        
        String loginUrl = "/api/session/login";
    	
    	String auth = "viking:pw";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	MvcResult result = mvc.perform(get(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();
    	
    	String token = result.getResponse().getHeader("x-auth-token");
        
        Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.onTheRoad), p, 1);
        
        String publisherName = "Viking Press";
        String url = "/api/publisher/" + publisherName + "/stock";
        
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(bookPage)));
	}
	
	@Test
	void givenSuccessfulLoginAndNameNotMatching_whenGetAvailableStock_thenReturnStatusForbidden() throws JsonProcessingException, Exception {
		entityManager.persist(BookMocks.onTheRoad);
        entityManager.flush();
        
        String loginUrl = "/api/session/login";
    	
    	String auth = "viking:pw";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	MvcResult result = mvc.perform(get(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();
    	
    	String token = result.getResponse().getHeader("x-auth-token");
        
        String publisherName = "Other Publisher Name";
        String url = "/api/publisher/" + publisherName + "/stock";
        
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
        ).andExpect(status()
                .isForbidden());
	}
	
	@Test
	void givenSuccessfullAdminOrBuyerLogin_whenGetAvailableStock_thenStatusForbidden() throws Exception {
		String password = "pw";
        String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
    	Buyer b = new Buyer("username", passwordHash);
		entityManager.persist(b);
        entityManager.flush();
        
        String loginUrl = "/api/session/login";
    	
    	String auth = "username:pw";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	MvcResult result = mvc.perform(get(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();
    	
    	String token = result.getResponse().getHeader("x-auth-token");
    	
    	String publisherName = "PublisherName";
        String url = "/api/publisher/" + publisherName + "/stock";
        
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
        ).andExpect(status()
                .isForbidden());
	}
	
	@Test
	void givenUnauthenticatedUser_whenGetAvailableStock_returnStatusUnauthorized() throws Exception {
		String publisherName = "PublisherName";
        String url = "/api/publisher/" + publisherName + "/stock";
        
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isUnauthorized());
	}
	
	@Test
	void givenSuccessfulLoginAndMatchingNameAndValidStockDto_whenupdateAvailableStock_thenReturnJson() throws Exception {
		entityManager.persist(BookMocks.onTheRoad);
        entityManager.flush();
        
        String loginUrl = "/api/session/login";
    	
    	String auth = "viking:pw";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	MvcResult result = mvc.perform(get(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();
    	
    	String token = result.getResponse().getHeader("x-auth-token");

        StockDto inStockDto = new StockDto(BookMocks.onTheRoad.getIsbn(), 5);
        StockDto outStockDto = new StockDto(BookMocks.onTheRoad.getIsbn(), 25);
        
        
        String publisherName = "Viking Press";
        String url = "/api/publisher/" + publisherName + "/stock";
        String body = toJson(inStockDto);
        
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
                .content(body)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(outStockDto)));
	}
	
	@Test
	void givenSuccessfulLoginAndNameNotMatching_whenUpdateAvailableStock_thenReturnStatusForbidden() throws Exception {
		entityManager.persist(BookMocks.onTheRoad);
        entityManager.flush();
        
        String loginUrl = "/api/session/login";
    	
    	String auth = "viking:pw";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	MvcResult result = mvc.perform(get(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();
    	
    	String token = result.getResponse().getHeader("x-auth-token");

        StockDto inStockDto = new StockDto(BookMocks.onTheRoad.getIsbn(), 5);
        
        String publisherName = "Wrong Publisher";
        String url = "/api/publisher/" + publisherName + "/stock";
        String body = toJson(inStockDto);
        
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
                .content(body)
        ).andExpect(status()
                .isForbidden());
	}
	
	@Test
	void givenSuccessfullAdminOrBuyerLogin_whenUpdateAvailableStock_thenStatusForbidden() throws Exception {
		String password = "pw";
        String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
    	Buyer b = new Buyer("username", passwordHash);
		entityManager.persist(b);
        entityManager.flush();
        
        String loginUrl = "/api/session/login";
    	
    	String auth = "username:pw";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	MvcResult result = mvc.perform(get(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();
    	
    	String token = result.getResponse().getHeader("x-auth-token");

        StockDto inStockDto = new StockDto(BookMocks.onTheRoad.getIsbn(), 5);
        String publisherName = "Viking Press";
        String url = "/api/publisher/" + publisherName + "/stock";
        String body = toJson(inStockDto);
        
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
                .content(body)
        ).andExpect(status()
                .isForbidden());
	}
	
	@Test
	void givenSuccessfulLoginAndMatchingNameAndBookOfAnotherPublisher_whenUpdateAvailableStock_thenStatusForbidden() throws Exception {
		Publisher littleBrown = new Publisher("little_brown", "30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4", "Little, Brown", "tin3");
		entityManager.persist(littleBrown);
		entityManager.persist(BookMocks.infiniteJest);
        entityManager.flush();
        
        String loginUrl = "/api/session/login";
    	
    	String auth = "viking:pw";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	MvcResult result = mvc.perform(get(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();
    	
    	String token = result.getResponse().getHeader("x-auth-token");

        StockDto inStockDto = new StockDto(BookMocks.infiniteJest.getIsbn(), 5);
        
        String publisherName = "Viking Press";
        String url = "/api/publisher/" + publisherName + "/stock";
        String body = toJson(inStockDto);
        
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
                .content(body)
        ).andExpect(status()
                .isForbidden());
	}
	
	@Test
	void givenSuccessfulLoginAndMatchingNameAndInexistentBookIsbn_whenUpdateAvailableStock_thenStatusNotFound() throws Exception {
        String loginUrl = "/api/session/login";
    	
    	String auth = "viking:pw";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	MvcResult result = mvc.perform(get(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();
    	
    	String token = result.getResponse().getHeader("x-auth-token");

        StockDto inStockDto = new StockDto(BookMocks.onTheRoad.getIsbn(), 5);
        
        String publisherName = "Viking Press";
        String url = "/api/publisher/" + publisherName + "/stock";
        String body = toJson(inStockDto);
        
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
                .content(body)
        ).andExpect(status()
                .isNotFound());
	}
	
	@Test
	void givenUnauthenticatedUser_whenUpdateAvailableStock_returnStatusUnauthorized() throws Exception {
		StockDto inStockDto = new StockDto(BookMocks.onTheRoad.getIsbn(), 5);
        String publisherName = "Viking Press";
        String url = "/api/publisher/" + publisherName + "/stock";
        String body = toJson(inStockDto);
        
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status()
                .isUnauthorized());
	}
	
}

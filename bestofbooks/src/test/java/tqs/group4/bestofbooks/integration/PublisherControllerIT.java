package tqs.group4.bestofbooks.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;
import static tqs.group4.bestofbooks.utils.Json.toJson;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
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
import tqs.group4.bestofbooks.dto.RevenueDTO;
import tqs.group4.bestofbooks.dto.StockDto;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.PublisherMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Buyer;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.model.Publisher;
import tqs.group4.bestofbooks.model.Revenue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
classes = BestofbooksApplication.class)
@AutoConfigureMockMvc
@Transactional
public class PublisherControllerIT {
	
	@Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;

    // Need to use these instead of the Mocks due to "detached entity cannot be persisted"
    private Pageable p = PageRequest.of(0, 20);
    private Revenue revenue1;
    private Revenue revenue2;
    private BookOrder bookOrder1;
    private BookOrder bookOrder2;
    private Order order1;

	@BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, publishers, books_orders, revenues").executeUpdate();
        order1 = new Order(
                "AC%EWRGER684654165",
                "77th st no 21, LA, CA, USA",
                10.00,
                buyer1
        );
        bookOrder1 = new BookOrder(BookMocks.onTheRoad, order1, 2);
        bookOrder2 = new BookOrder(BookMocks.infiniteJest, order1, 5);
        order1.addBookOrder(bookOrder1);
        order1.addBookOrder(bookOrder2);
        revenue1 = new Revenue(150, bookOrder1, "Publisher 1");
        revenue2 = new Revenue(300, bookOrder2, "Publisher 1");
        entityManager.persist(PublisherMocks.brown);
        entityManager.persist(PublisherMocks.viking);
        entityManager.persist(PublisherMocks.publisher1);
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
	
    

    @Test
    void givenExistentPublisherName_whenGetRevenuesByPublisherName_thenReturnJson() throws Exception {
        entityManager.persist(revenue1);
        entityManager.persist(revenue2);
        entityManager.flush();

        String knownPublisher = revenue1.getPublisherName();
        String url = "/api/publisher/" + knownPublisher + "/revenue";

        Page<RevenueDTO> revenueDTOPage = new PageImpl<>(
                Lists.newArrayList(RevenueDTO.fromRevenue(revenue1),
                        RevenueDTO.fromRevenue(revenue2)),
                p, 2);

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(revenueDTOPage)));
    }

    @Test
    void givenUnknownPublisherName_thenThrowHTTPStatusNotFound_forRevenues() throws Exception {
        String unknownPublisher = "none";
        String url = "/api/publisher/" + unknownPublisher + "/revenue";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenExistentPublisherName_whenGetTotalRevenuesByPublisherName_thenReturnJson() throws Exception {
        entityManager.persist(revenue1);
        entityManager.persist(revenue2);
        entityManager.flush();

        String knownPublisher = revenue1.getPublisherName();
        double totalRevenue = revenue1.getAmount() + revenue2.getAmount();
        String url = "/api/publisher/" + knownPublisher + "/revenue/total";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(totalRevenue)));
    }

    @Test
    void givenUnknownPublisherName_thenThrowHTTPStatusNotFound_forTotalInRevenues() throws Exception {
        String unknownPublisher = "none";
        String url = "/api/publisher/" + unknownPublisher + "/revenue/total";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}

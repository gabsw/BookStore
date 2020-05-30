package tqs.group4.bestofbooks.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tqs.group4.bestofbooks.BestofbooksApplication;
import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.model.Buyer;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tqs.group4.bestofbooks.utils.Json.toJson;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BestofbooksApplication.class)
@AutoConfigureMockMvc
@Transactional
public class SessionControllerIT {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
    private EntityManager entityManager;
	
	@BeforeEach
	public void before() {
		entityManager.createNativeQuery("TRUNCATE books, orders, commissions, publishers, books_orders, revenues, buyers, admin").executeUpdate();
	}

	
    @Test
    void givenValidUsernameAndPassword_whenLogin_thenReturnDto() throws JsonProcessingException, Exception {
        String url = "/api/session/login";
        String password = "password";
        String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
        Buyer b = new Buyer("username", passwordHash);
        
        entityManager.persist(b);
        entityManager.flush();
        
    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(new UserDto("username", "Buyer"))));
    	
    	entityManager.remove(b);
        entityManager.flush();
    	
    }
    
    @Test
    void givenInvalidUsernamePassword_whenLogin_thenHttpStatusForbidden() throws Exception {
    	String url = "/api/session/login";
    	String password = "password123";
        String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
    	Buyer b = new Buyer("username", passwordHash);
    	
    	entityManager.persist(b);
        entityManager.flush();
    	
    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andExpect(status()
                .isForbidden());
    	
    	entityManager.remove(b);
        entityManager.flush();
    	
    }
    
    @Test
    void givenInvalidAuthorizationHeader_whenLogin_thenHttpStatusForbidden() throws Exception {
    	String url = "/api/session/login";
    	

    	String auth = "username:passwordHash";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = new String( encodedAuth );
    	
    	mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andExpect(status()
                .isForbidden());
    }
    
    @Test
    void givenSuccessfullLogin_whenGetUserInfo_thenReturnUserDto() throws JsonProcessingException, Exception {
    	String url1 = "/api/session/login";
    	String url2 = "/api/session/user-info";
    	
    	String password = "password";
        String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
    	Buyer b = new Buyer("username", passwordHash);
    	
    	entityManager.persist(b);
        entityManager.flush();
    	
    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	MvcResult result = mvc.perform(get(url1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();
    	
    	String token = result.getResponse().getHeader("x-auth-token");
    	
    	mvc.perform(get(url2)
    			.contentType(MediaType.APPLICATION_JSON)
    			.header("x-auth-token", token))
    	.andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(new UserDto("username", "Buyer"))));
    	
    	entityManager.remove(b);
        entityManager.flush();
    	
    }
    
    @Test
    void givenRequestWithoutToken_whenGetUserInfo_thenThrowLoginRequiredException() throws JsonProcessingException, Exception {
    	String url = "/api/session/user-info";
    	    	
    	mvc.perform(get(url)
    			.contentType(MediaType.APPLICATION_JSON)
    			.header("x-auth-token", "token"))
    	.andExpect(status()
                .isUnauthorized());
    	
    }
    
    @Test
    void givenValidAuthHeaderAndDto_whenRegister_thenReturnCreatedUserDto() throws JsonProcessingException, Exception {
    	String url = "/api/session/register";
        UserDto dto = new UserDto("username", "Buyer");
        
    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	String body = toJson(dto);
    	
    	mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
                .content(body)
        ).andExpect(status()
                .isCreated())
           .andExpect(content().json(toJson(dto)))
           .andExpect(header().exists("x-auth-token"));
    }
    
    @Test
    void givenValidUserDtoAndAuthHeaderInconsistentUsername_whenRegister_thenHttpStatusBadRequest() throws Exception {
    	String url = "/api/session/register";
    	
    	UserDto dto = new UserDto("username123", "Buyer");
        
    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	String body = toJson(dto);
    	
    	mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
                .content(body)
        ).andExpect(status()
                .isBadRequest());
    }
    
    
    
    @Test
    void givenInvalidAuthorizationHeader_whenRegister_thenHttpStatusBadRequest() throws Exception {
    	String url = "/api/session/register";
    	UserDto dto = new UserDto("username", "Buyer");

    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = new String( encodedAuth );
    	String body = toJson(dto);
    	
    	mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
                .content(body)
        ).andExpect(status()
                .isBadRequest());
    }
    
    @Test
    void givenValidAuthHeaderAndDtoWithInvalidUserType_whenRegister_thenHttpStatusBadRequest() throws JsonProcessingException, Exception {
    	String url = "/api/session/register";
        UserDto dto = new UserDto("username", "NewUserType");
        
    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	String body = toJson(dto);
    	
    	mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
                .content(body)
        ).andExpect(status()
                .isBadRequest());
    }
	
}

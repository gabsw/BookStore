package tqs.group4.bestofbooks.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;

import tqs.group4.bestofbooks.BestofbooksApplication;
import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.LoginFailedException;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.model.Buyer;
import tqs.group4.bestofbooks.service.LoginServices;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.utils.Json.toJson;

import java.nio.charset.Charset;
import java.util.Base64;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BestofbooksApplication.class)
@AutoConfigureMockMvc
@Transactional
public class SessionControllerIT {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
    private EntityManager entityManager;

    
	/*@BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE SPRING_SESSION, SPRING_SESSION_ATTRIBUTES cascade;").executeUpdate();
    }*/
	
    @Test
    void givenValidUsernameAndPassword_whenLogin_thenReturnDto() throws JsonProcessingException, Exception {
        String url = "/api/session/login";
        Buyer b = new Buyer("username", "passwordHash");
        
        entityManager.persist(b);
        entityManager.flush();
        
    	String auth = "username:passwordHash";
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
    	Buyer b = new Buyer("username", "passwordHash123");
    	
    	entityManager.persist(b);
        entityManager.flush();
    	
    	String auth = "username:passwordHash";
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
    	
    	Buyer b = new Buyer("username", "passwordHash");
    	
    	entityManager.persist(b);
        entityManager.flush();
    	
    	String auth = "username:passwordHash";
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
    
    
	
}

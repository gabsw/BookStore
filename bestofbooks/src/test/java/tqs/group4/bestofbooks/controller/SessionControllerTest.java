package tqs.group4.bestofbooks.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;

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

import javax.servlet.http.HttpServletRequest;

@WebMvcTest(SessionController.class)
public class SessionControllerTest {

	@Autowired
    private MockMvc mvc;

    @MockBean
    private LoginServices loginService;

    @AfterEach
    public void after() {
        reset(loginService);
    }
    
    @Test
    void givenValidUsernameAndPassword_whenLogin_thenReturnDto() throws JsonProcessingException, Exception {
        String url = "/api/session/login";
        UserDto dto = new UserDto("username", "Buyer");
        given(loginService.loginUser("username", "password")).willReturn(dto);
        
    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(dto)));
    	
    	verify(loginService, VerificationModeFactory.times(1)).loginUser("username", "password");
    }
    
    @Test
    void givenInvalidUsernamePassword_whenLogin_thenHttpStatusForbidden() throws Exception {
    	String url = "/api/session/login";
    	
        given(loginService.loginUser("username", "password")).willThrow(new LoginFailedException("Login failed."));
    	
    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = "Basic " + new String( encodedAuth );
    	
    	mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andExpect(status()
                .isForbidden());
    	
    	verify(loginService, VerificationModeFactory.times(1)).loginUser("username", "password");
    }
	
    @Test
    void givenInvalidAuthorizationHeader_whenLogin_thenHttpStatusForbidden() throws Exception {
    	String url = "/api/session/login";

    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header = new String( encodedAuth );
    	
    	mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andExpect(status()
                .isForbidden());
    	
    	verify(loginService, VerificationModeFactory.times(0)).loginUser("username", "password");
    }
    
    @Test
    void givenInvalidAuthorizationHeaderNotBasic_whenLogin_thenHttpStatusForbidden() throws Exception {
    	String url = "/api/session/login";

    	String auth = "username:password";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header ="NotBasic " +new String( encodedAuth );
    	
    	mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andExpect(status()
                .isForbidden());
    	
    	verify(loginService, VerificationModeFactory.times(0)).loginUser("username", "password");
    }
    
    @Test
    void givenInvalidAuthorizationHeaderNoSeparaetionUsernamePasswrod_whenLogin_thenHttpStatusForbidden() throws Exception {
    	String url = "/api/session/login";

    	String auth = "usernamepassword";
    	byte[] encodedAuth = Base64.getEncoder().encode( 
                auth.getBytes(Charset.forName("US-ASCII")));
    	String header ="Basic " +new String( encodedAuth );
    	
    	mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andExpect(status()
                .isForbidden());
    	
    	verify(loginService, VerificationModeFactory.times(0)).loginUser("username", "password");
    }
    
    @Test
    void givenSuccessfullLoginToken_whenGetUserInfo_thenReturnUserDto() throws JsonProcessingException, Exception {
       	String url = "/api/session/user-info";
    	
    	UserDto dto = new UserDto("username", "Buyer");
    	
    	given(loginService.getSessionUsername(any(HttpServletRequest.class))).willReturn("username");
    	given(loginService.getUserDtoByUsername("username")).willReturn(dto);
    	    	
    	mvc.perform(get(url)
    			.contentType(MediaType.APPLICATION_JSON)
    			.header("x-auth-token", "token"))
    	.andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(dto)));
    	
    	verify(loginService, VerificationModeFactory.times(1)).getSessionUsername(any(HttpServletRequest.class));
    	verify(loginService, VerificationModeFactory.times(1)).getUserDtoByUsername("username");
    }
    
    @Test
    void givenRequestWithoutToken_whenGetUserInfo_thenThrowLoginRequiredException() throws JsonProcessingException, Exception {
    	String url = "/api/session/user-info";
    	
    	given(loginService.getSessionUsername(any(HttpServletRequest.class))).willReturn(null);
    	    	
    	mvc.perform(get(url)
    			.contentType(MediaType.APPLICATION_JSON)
    			.header("x-auth-token", "token"))
    	.andExpect(status()
                .isUnauthorized());
    	
    	verify(loginService, VerificationModeFactory.times(1)).getSessionUsername(any(HttpServletRequest.class));
    }
    
}

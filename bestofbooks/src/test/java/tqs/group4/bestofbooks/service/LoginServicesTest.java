package tqs.group4.bestofbooks.service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import com.google.common.hash.Hashing;

import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.exception.LoginFailedException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.model.Admin;
import tqs.group4.bestofbooks.model.Buyer;
import tqs.group4.bestofbooks.model.Publisher;
import tqs.group4.bestofbooks.repository.AdminRepository;
import tqs.group4.bestofbooks.repository.BuyerRepository;
import tqs.group4.bestofbooks.repository.PublisherRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

public class LoginServicesTest {
	
	@Mock
	private AdminRepository adminRepository;
	
	@Mock
	private PublisherRepository publisherRepository;
	
	@Mock
	private BuyerRepository buyerRepository;
	
	@InjectMocks
	private LoginServices loginService;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	void givenValidBuyerAccount_whenLogin_thenReturnBuyerUserDto() throws LoginFailedException, UserNotFoundException {
		String password = "password";
		String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString(); 
		when(buyerRepository.findById("username")).thenReturn(Optional.of(new Buyer("username", passwordHash)));
		when(adminRepository.findById("username")).thenReturn(Optional.empty());
		when(publisherRepository.findByUsername("username")).thenReturn(Optional.empty());
		
		UserDto dto = loginService.loginUser("username",password);
		
		assertEquals("username", dto.getUsername());
		assertEquals("Buyer", dto.getUserType());
		
		verify(buyerRepository, VerificationModeFactory.times(1)).findById("username");
		verify(adminRepository, VerificationModeFactory.times(0)).findById("username");
		verify(publisherRepository, VerificationModeFactory.times(0)).findByUsername("username");
	}
	
	@Test
	void givenValidAdminAccount_whenLogin_thenReturnAdminUserDto() throws LoginFailedException, UserNotFoundException {
		String password = "password";
		String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		when(buyerRepository.findById("username")).thenReturn(Optional.empty());
		when(adminRepository.findById("username")).thenReturn(Optional.of(new Admin("username", passwordHash)));
		when(publisherRepository.findByUsername("username")).thenReturn(Optional.empty());
		
		UserDto dto = loginService.loginUser("username",password);
		
		assertEquals("username", dto.getUsername());
		assertEquals("Admin", dto.getUserType());
		
		verify(buyerRepository, VerificationModeFactory.times(1)).findById("username");
		verify(adminRepository, VerificationModeFactory.times(1)).findById("username");
		verify(publisherRepository, VerificationModeFactory.times(0)).findByUsername("username");
	}
	
	@Test
	void givenValidPublisherAccount_whenLogin_thenReturnPublisherUserDto() throws LoginFailedException, UserNotFoundException {
		String password = "password";
		String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		when(buyerRepository.findById("username")).thenReturn(Optional.empty());
		when(adminRepository.findById("username")).thenReturn(Optional.empty());
		when(publisherRepository.findByUsername("username")).thenReturn(Optional.of(new Publisher("username", passwordHash, "name", "tin")));
		
		UserDto dto = loginService.loginUser("username",password);
		
		assertEquals("username", dto.getUsername());
		assertEquals("Publisher", dto.getUserType());
		assertEquals("name", dto.getAttributes().get("name"));
		assertEquals("tin", dto.getAttributes().get("tin"));
		
		verify(buyerRepository, VerificationModeFactory.times(1)).findById("username");
		verify(adminRepository, VerificationModeFactory.times(1)).findById("username");
		verify(publisherRepository, VerificationModeFactory.times(1)).findByUsername("username");
	}
	
	@Test
	void givenInvalidBuyerAccount_whenLogin_thenThrowLoginFailedException() {
		String password = "password123";
		String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		when(buyerRepository.findById("username")).thenReturn(Optional.of(new Buyer("username", passwordHash)));
		when(adminRepository.findById("username")).thenReturn(Optional.empty());
		when(publisherRepository.findByUsername("username")).thenReturn(Optional.empty());
		
		assertThrows(LoginFailedException.class,
                () -> loginService.loginUser("username","password"));
		
		verify(buyerRepository, VerificationModeFactory.times(1)).findById("username");
		verify(adminRepository, VerificationModeFactory.times(0)).findById("username");
		verify(publisherRepository, VerificationModeFactory.times(0)).findByUsername("username");
	}
	
	@Test
	void givenInvalidAdminAccount_whenLogin_thenThrowLoginFailedException() {
		String password = "password123";
		String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		when(buyerRepository.findById("username")).thenReturn(Optional.empty());
		when(adminRepository.findById("username")).thenReturn(Optional.of(new Admin("username", passwordHash)));
		when(publisherRepository.findByUsername("username")).thenReturn(Optional.empty());
		
		assertThrows(LoginFailedException.class,
                () -> loginService.loginUser("username","password"));
		
		verify(buyerRepository, VerificationModeFactory.times(1)).findById("username");
		verify(adminRepository, VerificationModeFactory.times(1)).findById("username");
		verify(publisherRepository, VerificationModeFactory.times(0)).findByUsername("username");
	}
	
	@Test
	void givenInvalidPublisherAccount_whenLogin_thenThrowLoginFailedException() {
		String password = "password123";
		String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		when(buyerRepository.findById("username")).thenReturn(Optional.empty());
		when(adminRepository.findById("username")).thenReturn(Optional.empty());
		when(publisherRepository.findByUsername("username")).thenReturn(Optional.of(new Publisher("username", passwordHash, "name", "tin")));
		
		assertThrows(LoginFailedException.class,
                () -> loginService.loginUser("username","password"));
		
		verify(buyerRepository, VerificationModeFactory.times(1)).findById("username");
		verify(adminRepository, VerificationModeFactory.times(1)).findById("username");
		verify(publisherRepository, VerificationModeFactory.times(1)).findByUsername("username");
	}
	
	@Test
	void givenNonExistentAccount_whenLogin_thenThrowUserNotFoundException() {
		when(buyerRepository.findById("username")).thenReturn(Optional.empty());
		when(adminRepository.findById("username")).thenReturn(Optional.empty());
		when(publisherRepository.findByUsername("username")).thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class,
                () -> loginService.loginUser("username","password"));
		
		verify(buyerRepository, VerificationModeFactory.times(1)).findById("username");
		verify(adminRepository, VerificationModeFactory.times(1)).findById("username");
		verify(publisherRepository, VerificationModeFactory.times(1)).findByUsername("username");
	}
	
	@Test
	void givenMissingArgument_whenLogin_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
                () -> loginService.loginUser("username",null));
	}
	
	@Test
	void givenValidBuyerUsername_whenGetUserDto_thenReturnBuyerDto() throws UserNotFoundException {
		when(buyerRepository.existsById("username")).thenReturn(true);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsById("username")).thenReturn(false);
		
		UserDto dto = loginService.getUserDtoByUsername("username");
		
		assertEquals("username", dto.getUsername());
		assertEquals("Buyer", dto.getUserType());
	}
	
	@Test
	void givenValidAdminUsername_whenGetUserDto_thenReturnAdminDto() throws UserNotFoundException {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(true);
		when(publisherRepository.existsById("username")).thenReturn(false);
		
		UserDto dto = loginService.getUserDtoByUsername("username");
		
		assertEquals("username", dto.getUsername());
		assertEquals("Admin", dto.getUserType());
	}
	
	@Test
	void givenValidPublisherUsername_whenGetUserDto_thenReturnPublisherDto() throws UserNotFoundException {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(true);
		when(publisherRepository.findByUsername("username")).thenReturn(Optional.of(new Publisher("username", "passwordHash", "name", "tin")));
		
		UserDto dto = loginService.getUserDtoByUsername("username");
		
		assertEquals("username", dto.getUsername());
		assertEquals("Publisher", dto.getUserType());
		assertEquals("name", dto.getAttributes().get("name"));
		assertEquals("tin", dto.getAttributes().get("tin"));
	}
	
	@Test
	void givenInvalidUsername_whenGetUserDto_thenThrowUserNotFoundException() {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		
		assertThrows(UserNotFoundException.class,
                () -> loginService.getUserDtoByUsername("username"));
	}
	
	@Test
	void givenUsernameMissing_whenGetUserDto_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
                () -> loginService.getUserDtoByUsername(null));
	}
	
	@Test
	void givenBuyerUsername_whenCheckUserIsBuyer_thenNoExceptionThrown() throws LoginRequiredException, ForbiddenUserException {
		when(buyerRepository.existsById("username")).thenReturn(true);
		
		loginService.checkUserIsBuyer("username");
	}
	
	@Test
	void givenNullUsername_whenCheckUserIsBuyer_thenThrowLoginRequiredException() {
		assertThrows(LoginRequiredException.class,
                () -> loginService.checkUserIsBuyer(null));
	}
	
	@Test
	void givenInvalidOrAdminOrPublisherUsername_whenCheckUserIsBuyer_thenForbiddenUserExceptionThrown() {
		when(buyerRepository.existsById("username")).thenReturn(false);
		
		assertThrows(ForbiddenUserException.class,
                () -> loginService.checkUserIsBuyer("username"));
	}
	
	@Test
	void givenAdminUsername_whenCheckUserIsAdmin_thenNoExceptionThrown() throws LoginRequiredException, ForbiddenUserException {
		when(adminRepository.existsById("username")).thenReturn(true);
		
		loginService.checkUserIsAdmin("username");
	}
	
	@Test
	void givenNullUsername_whenCheckUserIsAdmin_thenThrowLoginRequiredException() {
		assertThrows(LoginRequiredException.class,
                () -> loginService.checkUserIsAdmin(null));
	}
	
	@Test
	void givenInvalidOrBuyerOrPublisherUsername_whenCheckUserIsAdmin_thenForbiddenUserExceptionThrown() {
		when(adminRepository.existsById("username")).thenReturn(false);
		
		assertThrows(ForbiddenUserException.class,
                () -> loginService.checkUserIsAdmin("username"));
	}
	
	@Test
	void givenPublisherUsername_whenCheckUserIsPublisher_thenNoExceptionThrown() throws LoginRequiredException, ForbiddenUserException {
		when(publisherRepository.existsByUsername("username")).thenReturn(true);
		
		loginService.checkUserIsPublisher("username");
	}
	
	@Test
	void givenNullUsername_whenCheckUserIsPublisher_thenThrowLoginRequiredException() {
		assertThrows(LoginRequiredException.class,
                () -> loginService.checkUserIsPublisher(null));
	}
	
	@Test
	void givenInvalidOrBuyerOrAdminUsername_whenCheckUserIsPublisher_thenForbiddenUserExceptionThrown() {
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		
		assertThrows(ForbiddenUserException.class,
                () -> loginService.checkUserIsPublisher("username"));
	}

}

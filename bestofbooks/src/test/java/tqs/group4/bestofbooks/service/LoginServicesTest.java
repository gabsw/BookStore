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

import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.exception.LoginFailedException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.exception.RegistrationFailedException;
import tqs.group4.bestofbooks.exception.RepeatedPublisherNameException;
import tqs.group4.bestofbooks.exception.RepeatedUsernameException;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.mocks.PublisherMocks;
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

	private OrderDTO orderDTO = OrderDTO.fromOrder(OrderMocks.order1);
	
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
	
	@Test
	void givenValidBuyerUserDtoAndPassword_whenRegisterUser_thenReturnUserDto() throws RepeatedUsernameException, RepeatedPublisherNameException, RegistrationFailedException {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		
		UserDto dto = new UserDto("username", "Buyer");
		
		UserDto newUser = loginService.registerUser(dto, "username");
		
		assertEquals(dto, newUser);
	}
	
	@Test
	void givenValidAdminUserDtoAndPassword_whenRegisterUser_thenReturnUserDto() throws RepeatedUsernameException, RepeatedPublisherNameException, RegistrationFailedException {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		
		UserDto dto = new UserDto("username", "Admin");
		
		UserDto newUser = loginService.registerUser(dto, "username");
		
		assertEquals(dto, newUser);
	}
	
	@Test
	void givenValidPublisherUserDtoAndPassword_whenRegisterUser_thenReturnUserDto() throws RepeatedUsernameException, RepeatedPublisherNameException, RegistrationFailedException {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		when(publisherRepository.existsById("PublisherName")).thenReturn(false);
		
		UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "PublisherName");
		dto.addAttribute("tin", "tinValue");
		
		UserDto newUser = loginService.registerUser(dto, "username");
		
		assertEquals(dto, newUser);
	}
	
	@Test
	void givenUserDtoRepeatedUsername_whenRegisterUser_thenReturnUserDto() throws RepeatedUsernameException, RepeatedPublisherNameException, RegistrationFailedException {
		when(buyerRepository.existsById("username")).thenReturn(true);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		
		UserDto dto = new UserDto("username", "Buyer");
		
		assertThrows(RepeatedUsernameException.class,
                () -> loginService.registerUser(dto, "username"));
	}
	
	@Test
	void givenPublisherUserDtoRepeatedName_whenRegisterUser_thenReturnUserDto() throws RepeatedUsernameException, RepeatedPublisherNameException, RegistrationFailedException {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		when(publisherRepository.existsById("PublisherName")).thenReturn(true);
		
		UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "PublisherName");
		dto.addAttribute("tin", "tinValue");
		
		assertThrows(RepeatedPublisherNameException.class,
                () -> loginService.registerUser(dto, "username"));
	}
	
	@Test
	void givenPublisherUserDtoNullName_whenRegisterUser_thenReturnUserDto() throws RepeatedUsernameException, RepeatedPublisherNameException, RegistrationFailedException {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		when(publisherRepository.existsById("PublisherName")).thenReturn(false);
		
		UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("tin", "tinValue");
		
		assertThrows(RegistrationFailedException.class,
                () -> loginService.registerUser(dto, "username"));
	}
	
	@Test
	void givenPublisherUserDtoNullTin_whenRegisterUser_thenReturnUserDto() throws RepeatedUsernameException, RepeatedPublisherNameException, RegistrationFailedException {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		when(publisherRepository.existsById("PublisherName")).thenReturn(false);
		
		UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "PublisherName");
		
		assertThrows(RegistrationFailedException.class,
                () -> loginService.registerUser(dto, "username"));
	}
	
	@Test
	void givenInvalidUserTypeUserDto_whenRegisterUser_thenReturnUserDto() throws RepeatedUsernameException, RepeatedPublisherNameException, RegistrationFailedException {
		when(buyerRepository.existsById("username")).thenReturn(false);
		when(adminRepository.existsById("username")).thenReturn(false);
		when(publisherRepository.existsByUsername("username")).thenReturn(false);
		
		UserDto dto = new UserDto("username", "InvalidUserType");
		
		assertThrows(RegistrationFailedException.class,
                () -> loginService.registerUser(dto, "username"));
	}

	@Test
	void givenLoginWasNotProvided_whenCheckIfUserIsTheRightBuyer_thenLoginRequiredExceptionThrown() {
		assertThrows(LoginRequiredException.class,
				() -> loginService.checkIfUserIsTheRightBuyer("buyer1",null));
	}

	@Test
	void givenBuyerWasNotFound_whenCheckIfUserIsTheRightBuyer_thenForbiddenUserExceptionThrown() {
		when(buyerRepository.existsById("username")).thenReturn(false);

		assertThrows(ForbiddenUserException.class,
				() -> loginService.checkIfUserIsTheRightBuyer("username", "username"));
	}

	@Test
	void givenBuyerMismatch_whenCheckIfUserIsTheRightBuyer_thenForbiddenUserExceptionThrown() {
		when(buyerRepository.existsById("username")).thenReturn(true);

		assertThrows(ForbiddenUserException.class,
				() -> loginService.checkIfUserIsTheRightBuyer("buyer1", "username"));
	}

	@Test
	void givenLoginWasNotProvided_whenCheckIfUserIsTheRightBuyerForOrder_thenLoginRequiredExceptionThrown() {
		assertThrows(LoginRequiredException.class,
				() -> loginService.checkIfUserIsTheRightBuyerForOrder(orderDTO,null));
	}

	@Test
	void givenBuyerWasNotFound_whenCheckIfUserIsTheRightBuyerForOrder_thenForbiddenUserExceptionThrown() {
		when(buyerRepository.existsById("username")).thenReturn(false);

		assertThrows(ForbiddenUserException.class,
				() -> loginService.checkIfUserIsTheRightBuyerForOrder(orderDTO, "username"));
	}

	@Test
	void givenBuyerMismatch_whenCheckIfUserIsTheRightBuyerForOrder_thenForbiddenUserExceptionThrown() {
		when(buyerRepository.existsById("username3")).thenReturn(true);

		assertThrows(ForbiddenUserException.class,
				() -> loginService.checkIfUserIsTheRightBuyerForOrder(orderDTO, "username3"));
	}

	@Test
	void givenLoginWasNotProvided_whenCheckIfUserIsTheRightPublisher_thenLoginRequiredExceptionThrown() {
		assertThrows(LoginRequiredException.class,
				() -> loginService.checkIfUserIsTheRightPublisher("Publisher 1",null));
	}

	@Test
	void givenPublisherWasNotFound_whenCheckIfUserIsTheRightPublisher_thenForbiddenUserExceptionThrown() {
		when(publisherRepository.existsByUsername("pub1")).thenReturn(false);
		when(publisherRepository.findByUsername("pub1")).thenReturn(Optional.of(PublisherMocks.publisher1));

		assertThrows(ForbiddenUserException.class,
				() -> loginService.checkIfUserIsTheRightPublisher("Publisher 1", "pub1"));
	}

	@Test
	void givenPublisherMismatch_whenCheckIfUserIsTheRightPublisher_thenForbiddenUserExceptionThrown() {
		when(publisherRepository.existsByUsername("pub1")).thenReturn(true);
		when(publisherRepository.findByUsername("pub1")).thenReturn(Optional.of(PublisherMocks.publisher1));

		assertThrows(ForbiddenUserException.class,
				() -> loginService.checkIfUserIsTheRightPublisher("Publisher 1", "username"));
	}

	@Test
	void givenAllArgsAreValid_whenCheckIfUserIsTheRightBuyer_NoExceptionShouldBeThrown() {
		when(buyerRepository.existsById("buyer1")).thenReturn(true);
		assertDoesNotThrow(() -> loginService.checkIfUserIsTheRightBuyer("buyer1", "buyer1"));
	}

	@Test
	void givenAllArgsAreValid_whenCheckIfUserIsTheRightPublisher_NoExceptionShouldBeThrown() {
		when(publisherRepository.existsByUsername("pub1")).thenReturn(true);
		when(publisherRepository.findByUsername("pub1")).thenReturn(Optional.of(PublisherMocks.publisher1));
		assertDoesNotThrow(() -> loginService.checkIfUserIsTheRightPublisher("Publisher 1", "pub1"));
	}

}

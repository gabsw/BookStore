package tqs.group4.bestofbooks.service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.exception.LoginFailedException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.exception.RegistrationFailedException;
import tqs.group4.bestofbooks.exception.RepeatedPublisherNameException;
import tqs.group4.bestofbooks.exception.RepeatedUsernameException;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.model.Admin;
import tqs.group4.bestofbooks.model.Buyer;
import tqs.group4.bestofbooks.model.Publisher;
import tqs.group4.bestofbooks.repository.AdminRepository;
import tqs.group4.bestofbooks.repository.BuyerRepository;
import tqs.group4.bestofbooks.repository.PublisherRepository;

@Transactional
@Service
public class LoginServices {
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	PublisherRepository publisherRepository;
	
	@Autowired
	BuyerRepository buyerRepository;
	
	static String loginRequiredMessage = "Login required for this request.";
	static String userForbiddenMessage = "User not allowed.";
	
	public UserDto loginUser(String username, String password) throws LoginFailedException, UserNotFoundException {
		String loginFailedMessage = "Login failed.";
		if (username == null || password == null) {
            throw new IllegalArgumentException("User (password or username) is not defined.");
        }
		String passwordHash  = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
        Optional<Buyer> optBuyer = buyerRepository.findById(username);
        if (optBuyer.isPresent()) {
        	if (!optBuyer.get().getPasswordHash().equals(passwordHash)) {
        		throw new LoginFailedException(loginFailedMessage);
        	}
        	Buyer b = optBuyer.get();
        	return new UserDto(b.getUsername(),"Buyer");
        }
        
        Optional<Admin> optAdmin = adminRepository.findById(username);
        if (optAdmin.isPresent()) {
        	if (!optAdmin.get().getPasswordHash().equals(passwordHash)) {
        		throw new LoginFailedException(loginFailedMessage);
        	}
        	Admin a = optAdmin.get();
        	return new UserDto(a.getUsername(),"Admin");
        }
        
        Optional<Publisher> optPublisher = publisherRepository.findByUsername(username);
        if (optPublisher.isPresent()) {
        	if (!optPublisher.get().getPasswordHash().equals(passwordHash)) {
        		throw new LoginFailedException(loginFailedMessage);
        	}
        	Publisher p = optPublisher.get();
        	UserDto dto = new UserDto(p.getUsername(),"Publisher");
        	dto.addAttribute("name", p.getName());
        	dto.addAttribute("tin", p.getTin());
        	return dto;
        }
        
        throw new UserNotFoundException("User not found " + username);
	}
	
	public UserDto registerUser(UserDto newUser, String password) throws RepeatedUsernameException, RepeatedPublisherNameException, RegistrationFailedException {
		
		if (buyerRepository.existsById(newUser.getUsername()) || adminRepository.existsById(newUser.getUsername()) || publisherRepository.existsByUsername(newUser.getUsername())) {
			throw new RepeatedUsernameException("Username already exists.");
		}
		if (newUser.getUserType().equals("Publisher") && publisherRepository.existsById(newUser.getAttributes().get("name"))) {
			throw new RepeatedPublisherNameException("Publisher name already exists.");
		}
		
		String passwordHash = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();;
		
		if (newUser.getUserType().equals("Buyer")) {
			Buyer b = new Buyer(newUser.getUsername(), passwordHash);
			buyerRepository.save(b);
		}
		else if (newUser.getUserType().equals("Admin")) {
			Admin a = new Admin(newUser.getUsername(), passwordHash);
			adminRepository.save(a);
		}
		else if (newUser.getUserType().equals("Publisher")) {
			if (newUser.getAttributes().get("name") == null || newUser.getAttributes().get("tin") == null) {
				throw new RegistrationFailedException("Invalid publisher creation arguments.");
			}
			Publisher p = new Publisher(newUser.getUsername(), passwordHash, newUser.getAttributes().get("name"), newUser.getAttributes().get("tin"));
			publisherRepository.save(p);
		}
		else {
			throw new RegistrationFailedException("Invalid user type.");
		}
		
		return newUser;
	}
	
	public UserDto getUserDtoByUsername(String username) throws UserNotFoundException {
	   	if (username == null) {
	           throw new IllegalArgumentException("Username is not defined.");
	       } else {
	           if (buyerRepository.existsById(username)) {
	           	return new UserDto(username,"Buyer");
	           }
	           else if (adminRepository.existsById(username)) {
	           	return new UserDto(username,"Admin");
	           }
	           else if (publisherRepository.existsByUsername(username)) {
	           	Optional<Publisher> op = publisherRepository.findByUsername(username);
	           	if (op.isPresent()) {
	           		Publisher p = op.get();
	           		UserDto dto = new UserDto(p.getUsername(),"Publisher");
	            	dto.addAttribute("name", p.getName());
	            	dto.addAttribute("tin", p.getTin());
	            	return dto;
	           	}
	           }
	           
	           throw new UserNotFoundException("User not found " + username);
	       }
	   }
	
	public void setSessionUsername(HttpServletRequest request, String username) {
	 request.getSession().setAttribute("username", username);
	}
	
	public String getSessionUsername(HttpServletRequest request) {
	 return (String) request.getSession().getAttribute("username");
	}
	
	public void checkUserIsBuyer(String username) throws LoginRequiredException, ForbiddenUserException {
	 if (username == null) {
		 throw new LoginRequiredException(loginRequiredMessage);
	    }
	 else {
		 if (!buyerRepository.existsById(username)) {
			throw new ForbiddenUserException(userForbiddenMessage); 
		 }
	 }
	}
	
	public void checkUserIsAdmin(String username) throws LoginRequiredException, ForbiddenUserException {
	 if (username == null) {
		 throw new LoginRequiredException(loginRequiredMessage);
	    }
	 else {
		 if (!adminRepository.existsById(username)) {
			throw new ForbiddenUserException(userForbiddenMessage); 
		 }
	 }
	}
	
	public void checkUserIsPublisher(String username) throws LoginRequiredException, ForbiddenUserException {
	 if (username == null) {
		 throw new LoginRequiredException(loginRequiredMessage);
	    }
	 else {
		 if (!publisherRepository.existsByUsername(username)) {
			throw new ForbiddenUserException(userForbiddenMessage); 
		 }
	 }
	}

}

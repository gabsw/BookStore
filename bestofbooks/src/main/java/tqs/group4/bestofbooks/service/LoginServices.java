package tqs.group4.bestofbooks.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.LoginFailedException;
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
	
	public UserDto loginUser(String username, String passwordHash) throws LoginFailedException, UserNotFoundException {
		if (username == null || passwordHash == null) {
            throw new IllegalArgumentException("User (password or username) is not defined.");
        }
        Optional<Buyer> opt_buyer = buyerRepository.findById(username);
        if (opt_buyer.isPresent()) {
        	if (!opt_buyer.get().getPassword_hash().equals(passwordHash)) {
        		throw new LoginFailedException("Login failed.");
        	}
        	Buyer b = opt_buyer.get();
        	return new UserDto(b.getUsername(),"Buyer");
        }
        
        Optional<Admin> opt_admin = adminRepository.findById(username);
        if (opt_admin.isPresent()) {
        	if (!opt_admin.get().getPassword_hash().equals(passwordHash)) {
        		throw new LoginFailedException("Login failed.");
        	}
        	Admin a = opt_admin.get();
        	return new UserDto(a.getUsername(),"Admin");
        }
        
        Optional<Publisher> opt_publisher = publisherRepository.findByUsername(username);
        if (opt_publisher.isPresent()) {
        	if (!opt_publisher.get().getPassword_hash().equals(passwordHash)) {
        		throw new LoginFailedException("Login failed.");
        	}
        	Publisher p = opt_publisher.get();
        	UserDto dto = new UserDto(p.getUsername(),"Publisher");
        	dto.addAttribute("name", p.getName());
        	dto.addAttribute("tin", p.getTin());
        	return dto;
        }
        
        throw new UserNotFoundException("User not found " + username);
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
	            	Publisher p = publisherRepository.findByUsername(username).get();
	            	UserDto dto = new UserDto(p.getUsername(),"Publisher");
	            	dto.addAttribute("name", p.getName());
	            	dto.addAttribute("tin", p.getTin());
	            	return dto;
	            }
	            
	            throw new UserNotFoundException("User not found " + username);
	        }
	    }

}

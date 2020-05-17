package tqs.group4.bestofbooks.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;
import tqs.group4.bestofbooks.service.LoginServices;
import tqs.group4.bestofbooks.service.StockService;

@CrossOrigin
@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
	
	@Autowired
    private StockService stockService;
	
	@Autowired
	private LoginServices loginService;
	
	@GetMapping("/stock")
    public Page<Book> getAvailableStock(Pageable pageable, HttpServletRequest request) throws UserNotFoundException {
        String username = loginService.getSessionUsername(request);
        UserDto dto = loginService.getUserDtoByUsername(username);
        //TODO: Mudar isto com as funções de permissões do loginServices após o novo pullRequest for aceite.
        
		return stockService.getPublisherBooks(dto.getAttributes().get("name"), pageable);
    }

}

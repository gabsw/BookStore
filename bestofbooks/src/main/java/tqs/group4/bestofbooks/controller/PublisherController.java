package tqs.group4.bestofbooks.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import tqs.group4.bestofbooks.dto.StockDto;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.service.StockService;
import tqs.group4.bestofbooks.dto.RevenueDTO;
import tqs.group4.bestofbooks.service.RevenueService;



@CrossOrigin
@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
	
	@Autowired
    private StockService stockService;

    @Autowired
    private RevenueService revenueService;
	
	
	@GetMapping("{publisherName}/stock")
    public Page<Book> getAvailableStock(@PathVariable String publisherName, Pageable pageable, HttpServletRequest request) throws UserNotFoundException, LoginRequiredException, ForbiddenUserException {
        
		return stockService.getPublisherBooks(publisherName, request, pageable);
    }
	
	@PutMapping("{publisherName}/stock")
	public ResponseEntity<StockDto> updateAvailableStock(@PathVariable String publisherName, @Valid @RequestBody StockDto stockDto, HttpServletRequest request) throws LoginRequiredException, ForbiddenUserException, UserNotFoundException, BookNotFoundException{
		StockDto dto = stockService.updateBookStock(publisherName, stockDto, request);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

    @GetMapping("/{publisherName}/revenue")
    public Page<RevenueDTO> getRevenuesByPublisher(@PathVariable String publisherName, Pageable pageable)
            throws UserNotFoundException {
        return revenueService.getRevenuesByPublisher(publisherName, pageable);
    }

    @GetMapping("/{publisherName}/revenue/total")
    public Double getRevenuesTotalByPublisher(@PathVariable String publisherName)
            throws UserNotFoundException {
        return revenueService.getRevenuesTotalByPublisher(publisherName);
    }
}

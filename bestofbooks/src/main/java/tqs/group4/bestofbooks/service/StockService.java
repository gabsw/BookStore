package tqs.group4.bestofbooks.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;

@Transactional
@Service
public class StockService {

	@Autowired
    private BookRepository bookRepository;
	
	public Page<Book> getPublisherBooks(String publisherName, Pageable pageable){
		return bookRepository.findByPublisherName(publisherName, pageable);
	}
	
}

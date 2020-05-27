package tqs.group4.bestofbooks.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tqs.group4.bestofbooks.model.Book;

public class BookListDTOTest {
	
	private BookListDTO dto;
	private List<Book> l;
	
	@BeforeEach
	public void setUp() {
		l = new ArrayList<>();
		l.add(new Book("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5, "Travelogue", "Publisher"));
		l.add(new Book("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4, "Travelogue", "Publisher"));
		dto = new BookListDTO(l);
	}
	
	@Test
	public void checkGetBooks() {
		assertEquals(l, dto.getBooks());
	}
	
	@Test
	public void checkSetBooks() {
		List<Book> nl = new ArrayList<>();
		dto.setBooks(nl);
		assertEquals(nl, dto.getBooks());
	}
	
	@Test
	public void checkHashCode() {
		List<Book> l1 = new ArrayList<>();
		l1.add(new Book("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5, "Travelogue", "Publisher"));
		l1.add(new Book("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4, "Travelogue", "Publisher"));;
		BookListDTO dto1 = new BookListDTO(l1);
		BookListDTO dto2 = new BookListDTO();
		
		assertEquals(dto.hashCode(), dto1.hashCode());
		assertNotEquals(dto.hashCode(), dto2.hashCode());
	}
	
	@Test
	public void checkEquals() {
		List<Book> l1 = new ArrayList<>();
		l1.add(new Book("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5, "Travelogue", "Publisher"));
		l1.add(new Book("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4, "Travelogue", "Publisher"));;
		BookListDTO dto1 = new BookListDTO(l1);
		BookListDTO dto2 = new BookListDTO();
		
		assertEquals(true, dto1.equals(dto));
		assertEquals(false, dto2.equals(dto));
	}
	
	@Test
	public void checkAddBookDTO() {
		Book newDto = new Book("9876543211235", "Title 3", "Author 3", "Description 3", 15, 6, "Travelogue", "Publisher");
		List<Book> l1 = new ArrayList<>();
		l1.add(new Book("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5, "Travelogue", "Publisher"));
		l1.add(new Book("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4, "Travelogue", "Publisher"));;
		l1.add(newDto);
		dto.addBook(newDto);
		
		assertEquals(l1, dto.getBooks());
	}

}

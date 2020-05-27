package tqs.group4.bestofbooks.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookDTOListTest {
	
	private BookDTOList dto;
	private List<BookDTO> l;
	
	@BeforeEach
	public void setUp() {
		l = new ArrayList<>();
		l.add(new BookDTO("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5, "Travelogue"));
		l.add(new BookDTO("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4, "Travelogue"));
		dto = new BookDTOList(l);
	}
	
	@Test
	public void checkGetBooks() {
		assertEquals(l, dto.getBooks());
	}
	
	@Test
	public void checkSetBooks() {
		List<BookDTO> nl = new ArrayList<>();
		dto.setBooks(nl);
		assertEquals(nl, dto.getBooks());
	}
	
	@Test
	public void checkHashCode() {
		List<BookDTO> l1 = new ArrayList<>();
		l1.add(new BookDTO("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5, "Travelogue"));
		l1.add(new BookDTO("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4, "Travelogue"));
		BookDTOList dto1 = new BookDTOList(l1);
		List<BookDTO> l2 = new ArrayList<>();
		BookDTOList dto2 = new BookDTOList(l2);
		
		assertEquals(dto.hashCode(), dto1.hashCode());
		assertNotEquals(dto.hashCode(), dto2.hashCode());
	}
	
	@Test
	public void checkEquals() {
		List<BookDTO> l1 = new ArrayList<>();
		l1.add(new BookDTO("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5, "Travelogue"));
		l1.add(new BookDTO("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4, "Travelogue"));
		BookDTOList dto1 = new BookDTOList(l1);
		BookDTOList dto2 = new BookDTOList();
		
		assertEquals(true, dto1.equals(dto));
		assertEquals(false, dto2.equals(dto));
	}
	
	@Test
	public void checkAddBookDTO() {
		BookDTO newDto = new BookDTO("9876543211235", "Title 3", "Author 3", "Description 3", 15, 6, "Travelogue");
		List<BookDTO> l1 = new ArrayList<>();
		l1.add(new BookDTO("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5, "Travelogue"));
		l1.add(new BookDTO("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4, "Travelogue"));
		l1.add(newDto);
		dto.addBookDTO(newDto);
		
		assertEquals(l1, dto.getBooks());
	}

}

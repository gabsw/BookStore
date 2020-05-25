package tqs.group4.bestofbooks.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookDTOTest {

	private BookDTO dto;
	
	@BeforeEach
	public void setUp() {
		dto = new BookDTO("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5,
                "Travelogue");
	}
	
	@Test
	public void checkGetIsbn() {
		assertEquals("1234567891234", dto.getIsbn());
	}
	
	@Test
	public void checkSetIsbn() {
		dto.setIsbn("3216549873216");
		assertEquals("3216549873216", dto.getIsbn());
	}
	
	@Test
	public void checkGetTitle() {
		assertEquals("Title 1", dto.getTitle());
	}
	
	@Test
	public void checkSetTitle() {
		dto.setTitle("Title 2");
		assertEquals("Title 2", dto.getTitle());
	}
	
	@Test
	public void checkGetAuthor() {
		assertEquals("Author 1", dto.getAuthor());
	}
	
	@Test
	public void checkSetAuthor() {
		dto.setAuthor("Author 2");
		assertEquals("Author 2", dto.getAuthor());
	}
	
	@Test
	public void checkGetDescription() {
		assertEquals("Description 1", dto.getDescription());
	}
	
	@Test
	public void checkSetDescription() {
		dto.setDescription("Description 2");
		assertEquals("Description 2", dto.getDescription());
	}
	
	@Test
	public void checkGetPrice() {
		assertEquals(20, dto.getPrice());
	}
	
	@Test
	public void checkSetPrice() {
		dto.setPrice(25);
		assertEquals(25, dto.getPrice());
	}
	
	@Test
	public void checkGetQuantity() {
		assertEquals(5, dto.getQuantity());
	}
	
	@Test
	public void checkSetQuantity() {
		dto.setQuantity(7);
		assertEquals(7, dto.getQuantity());
	}
	
	@Test
	public void checkGetCategory() {
		assertEquals("Travelogue", dto.getCategory());
	}
	
	@Test
	public void checkSetCategory() {
		dto.setCategory("Category");
		assertEquals("Category", dto.getCategory());
	}
	
	@Test
	public void checkHashCode() {
		BookDTO newDto = new BookDTO("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4,
                "Travelogue");
		Assertions.assertNotEquals(dto.hashCode(), newDto.hashCode());
		BookDTO sameDto = new BookDTO("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5,
                "Travelogue");
		assertEquals(dto.hashCode(), sameDto.hashCode());
	}
	
	@Test
	public void checkEquals() {
		BookDTO newDto = new BookDTO("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4,
                "Travelogue");
		assertEquals(false, newDto.equals(dto));
		BookDTO sameDto = new BookDTO("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5,
                "Travelogue");
		assertEquals(true, sameDto.equals(dto));
	}
	
}

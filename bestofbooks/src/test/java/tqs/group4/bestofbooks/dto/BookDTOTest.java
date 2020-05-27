package tqs.group4.bestofbooks.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tqs.group4.bestofbooks.model.Book;

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
		/*BookDTO nullDto = new BookDTO(null, null, null, null, 10, 10, null);
		Assertions.assertNotEquals(dto.hashCode(), nullDto.hashCode());*/
	}
	
	@Test
	public void checkEquals() {
		BookDTO newDto = new BookDTO("9876543211234", "Title 2", "Author 2", "Description 2", 19, 4,
                "Travelogue");
		assertEquals(false, newDto.equals(dto));
		BookDTO sameDto = new BookDTO("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5,
                "Travelogue");
		assertEquals(true, sameDto.equals(dto));
		assertEquals(true, dto.equals(dto));
		/*assertEquals(false, dto.equals(null));
		assertEquals(false, dto.equals(new UserDto("username", "Buyer")));
		sameDto.setTitle("AnotherTitle");
		assertEquals(false, dto.equals(sameDto));
		dto.setTitle(null);
		assertEquals(false, dto.equals(sameDto));
		sameDto.setTitle(null);
		assertEquals(true, dto.equals(sameDto));
		sameDto.setQuantity(10);
		assertEquals(false, dto.equals(sameDto));
		sameDto.setPrice(10);
		assertEquals(false, dto.equals(sameDto));
		sameDto.setIsbn("1234567896482");
		assertEquals(false, dto.equals(sameDto));
		dto.setIsbn(null);
		assertEquals(false, dto.equals(sameDto));
		sameDto.setIsbn(null);
		assertEquals(false, dto.equals(sameDto));
		sameDto.setDescription("New Description");
		assertEquals(false, dto.equals(sameDto));
		dto.setDescription(null);
		assertEquals(false, dto.equals(sameDto));
		sameDto.setDescription(null);
		assertEquals(false, dto.equals(sameDto));
		sameDto.setCategory("NewCategory");
		assertEquals(false, dto.equals(sameDto));
		dto.setCategory(null);
		assertEquals(false, dto.equals(sameDto));
		sameDto.setCategory(null);
		assertEquals(false, dto.equals(sameDto));*/
	}
	
	@Test
	public void testGetBookObject() {
		Book b = dto.getBookObject("Publisher");
		assertEquals(dto.getIsbn(), b.getIsbn());
		assertEquals(dto.getTitle(), b.getTitle());
		assertEquals(dto.getAuthor(), b.getAuthor());
		assertEquals(dto.getDescription(), b.getDescription());
		assertEquals(dto.getPrice(), b.getPrice());
		assertEquals(dto.getQuantity(), b.getQuantity());
		assertEquals(dto.getCategory(), b.getCategory());
		assertEquals("Publisher", b.getPublisherName());
	}
	
}

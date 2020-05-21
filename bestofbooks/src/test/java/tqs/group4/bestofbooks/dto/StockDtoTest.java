package tqs.group4.bestofbooks.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class StockDtoTest {
	
	private StockDto dto;
	
	@BeforeEach
	public void setUp() {
		dto = new StockDto("1234567891234", 5);
	}
	
	@Test
	public void checkGetIsbn() {
		assertEquals("1234567891234", dto.getIsbn());
	}
	
	@Test
	public void checkGetQuantity() {
		assertEquals(5, dto.getQuantity());
	}
	
	@Test
	public void checkSetIsbn() {
		dto.setIsbn("9876543213216");
		assertEquals("9876543213216", dto.getIsbn());
	}
	
	@Test
	public void checkSetQuantity() {
		dto.setQuantity(10);
		assertEquals(10, dto.getQuantity());
	}
	
	@Test
	public void testEquals() {
		assertEquals(true, dto.equals(dto));
		assertEquals(false, dto.equals(null));
		StockDto newDto = new StockDto("1234567891234", 5);
		assertEquals(true, dto.equals(newDto));
		newDto.setQuantity(10);
		assertEquals(false, dto.equals(newDto));
	}
	
	@Test
	public void hashCodeTest() {
		StockDto newDto = new StockDto("1234567891234", 5);
		assertEquals(dto.hashCode(), newDto.hashCode());
	}

}

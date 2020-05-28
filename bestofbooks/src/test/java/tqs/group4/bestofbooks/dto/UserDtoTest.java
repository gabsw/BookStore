package tqs.group4.bestofbooks.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDtoTest {
	
	private UserDto dto;
	
	@BeforeEach
	public void setUp() {
		dto = new UserDto("username", "Buyer");
	}
	
	@Test
	public void checkGetUsername() {
		assertEquals("username", dto.getUsername());
	}
	
	@Test
	public void checkGetUserType() {
		assertEquals("Buyer", dto.getUserType());
	}
	
	@Test
	public void checkSetUsername() {
		dto.setUsername("newUsername");
		assertEquals("newUsername", dto.getUsername());
	}
	
	@Test
	public void checkSetUserType() {
		dto.setUserType("Admin");
		assertEquals("Admin", dto.getUserType());
	}
	
	@Test
	public void checkGetAttributes() {
		assertEquals(new HashMap<>(), dto.getAttributes());
	}
	
	@Test
	public void checkSetAttributes() {
		Map<String, String> newAttributes = new HashMap<>();
		newAttributes.put("attr", "value");
		dto.setAttributes(newAttributes);
		assertEquals(newAttributes, dto.getAttributes());
	}
	
	@Test
	public void checkAddAttribute() {
		Map<String, String> newAttributes = new HashMap<>();
		newAttributes.put("attr", "value");
		dto.addAttribute("attr", "value");
		assertEquals(newAttributes, dto.getAttributes());
	}
	
	@Test
	public void checkHashCode() {
		UserDto newUser = new UserDto("usernameAdmin", "Admin");
		UserDto sameUser = new UserDto("username", "Buyer");
		UserDto nullUser = new UserDto(null, null);
		nullUser.setAttributes(null);
		
		assertEquals(dto.hashCode(), sameUser.hashCode());
		assertNotEquals(dto.hashCode(), newUser.hashCode());
		assertNotEquals(dto.hashCode(), nullUser.hashCode());
	}
	
	@Test
	public void checkEquals() {
		UserDto sameUser = new UserDto("username", "Buyer");
		
		assertEquals(true, dto.equals(dto));
		assertEquals(true, dto.equals(sameUser));
		assertEquals(false, dto.equals(null));
		assertEquals(false, dto.equals(new StockDto("1234567891234", 5)));
		sameUser.setUsername("anotherUsername");
		assertEquals(false, dto.equals(sameUser));
		dto.setUsername(null);
		assertEquals(false, dto.equals(sameUser));
		sameUser.setUsername(null);
		assertEquals(true, dto.equals(sameUser));
		sameUser.setUserType("Admin");
		assertEquals(false, dto.equals(sameUser));
		dto.setUserType(null);
		assertEquals(false, dto.equals(sameUser));
		sameUser.setUserType(null);
		assertEquals(true, dto.equals(sameUser));
		sameUser.addAttribute("newAttr", "Attribute");
		assertEquals(false, dto.equals(sameUser));
		dto.setAttributes(null);
		assertEquals(false, dto.equals(sameUser));
		sameUser.setAttributes(null);
		assertEquals(true, dto.equals(sameUser));
		
		
	}

}

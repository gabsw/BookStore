package tqs.group4.bestofbooks.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}

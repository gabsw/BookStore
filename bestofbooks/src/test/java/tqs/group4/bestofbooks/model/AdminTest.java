package tqs.group4.bestofbooks.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminTest {
	
	private Admin admin;
	
	@BeforeEach
	public void setUp() {
		admin = new Admin("username", "passwordHash");
	}
	
	@Test
	public void checkGetUsername() {
		assertEquals("username", admin.getUsername());
	}
	
	@Test
	public void checkGetPasswordHash() {
		assertEquals("passwordHash", admin.getPassword_hash());
	}
	
	@Test
	public void checkSetUsername() {
		admin.setUsername("newUsername");
		assertEquals("newUsername", admin.getUsername());
	}
	
	@Test
	public void checkSetPasswordHash() {
		admin.setPassword_hash("newHash");
		assertEquals("newHash", admin.getPassword_hash());
	}

}

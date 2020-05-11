package tqs.group4.bestofbooks.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuyerTest {
	
	private Buyer buyer;
	
	@BeforeEach
	public void setUp() {
		buyer = new Buyer("username", "passwordHash");
	}
	
	@Test
	public void checkGetUsername() {
		assertEquals("username", buyer.getUsername());
	}
	
	@Test
	public void checkGetPasswordHash() {
		assertEquals("passwordHash", buyer.getPassword_hash());
	}
	
	@Test
	public void checkSetUsername() {
		buyer.setUsername("newUsername");
		assertEquals("newUsername", buyer.getUsername());
	}
	
	@Test
	public void checkSetPasswordHash() {
		buyer.setPassword_hash("newHash");
		assertEquals("newHash", buyer.getPassword_hash());
	}

}

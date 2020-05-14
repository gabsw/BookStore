package tqs.group4.bestofbooks.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PublisherTest {

	private Publisher publisher;
	
	@BeforeEach
	public void setUp() {
		publisher = new Publisher("username", "passwordHash","name", "tin");
	}
	
	@Test
	public void checkGetUsername() {
		assertEquals("username", publisher.getUsername());
	}
	
	@Test
	public void checkGetPasswordHash() {
		assertEquals("passwordHash", publisher.getPasswordHash());
	}
	
	@Test
	public void checkGetName() {
		assertEquals("name", publisher.getName());
	}
	
	@Test
	public void checkGetTin() {
		assertEquals("tin", publisher.getTin());
	}
	
	@Test
	public void checkSetUsername() {
		publisher.setUsername("newUsername");
		assertEquals("newUsername", publisher.getUsername());
	}
	
	@Test
	public void checkSetPasswordHash() {
		publisher.setPasswordHash("newHash");
		assertEquals("newHash", publisher.getPasswordHash());
	}
	
	@Test
	public void checkSetName() {
		publisher.setName("newName");
		assertEquals("newName", publisher.getName());
	}
	
	@Test
	public void checkSetTin() {
		publisher.setTin("newTin");
		assertEquals("newTin", publisher.getTin());
	}
	
}

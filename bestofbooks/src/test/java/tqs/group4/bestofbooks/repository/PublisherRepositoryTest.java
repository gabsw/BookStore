package tqs.group4.bestofbooks.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.model.Admin;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.model.Publisher;

@SpringBootTest
@Transactional
public class PublisherRepositoryTest {

	@Autowired
    private EntityManager entityManager;

    @Autowired
    private PublisherRepository publisherRepository;
    
    @BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, publishers, books_orders, revenues").executeUpdate();
    }
    
    @Test
    void givenExistentUsername_whenFindByUsername_returnBuyer() {
    	Publisher b = new Publisher("username","password","name", "tin");
    	entityManager.persist(b);
        entityManager.flush();

        Optional<Publisher> queryResults = publisherRepository.findByUsername("username");
        assertEquals(b, queryResults.get());
        
        entityManager.remove(b);
        entityManager.flush();
    }
    
    @Test
    void givenInexistentUsername_whenFindByUsername_returnEmpty() {
    	Optional<Publisher> queryResults = publisherRepository.findByUsername("username");
        assertEquals(false, queryResults.isPresent());
    }
    
    @Test
    void givenExistentName_whenFindById_returnBuyer() {
    	Publisher b = new Publisher("username","password","name", "tin");
    	entityManager.persist(b);
        entityManager.flush();

        Optional<Publisher> queryResults = publisherRepository.findById("name");
        assertEquals(b, queryResults.get());
        
        entityManager.remove(b);
        entityManager.flush();
    }
    
    @Test
    void givenInexistentName_whenFindById_returnEmpty() {
    	Optional<Publisher> queryResults = publisherRepository.findById("name");
        assertEquals(false, queryResults.isPresent());
    }
    
    @Test
    void given3Buyers_whenFindAll_return3Buyers() {
    	Publisher b1 = new Publisher("username1","password1","name1", "tin1");
    	Publisher b2 = new Publisher("username2","password2","name2", "tin2");
    	Publisher b3 = new Publisher("username3","password3","name3", "tin3");
    	
    	entityManager.persist(b1);
    	entityManager.persist(b2);
    	entityManager.persist(b3);
        entityManager.flush();
    	
    	List<Publisher> l = publisherRepository.findAll();
    	
    	assertEquals(3, l.size());
    	assertEquals(true, l.contains(b1));
    	assertEquals(true, l.contains(b2));
    	assertEquals(true, l.contains(b3));
    }
    
    @Test
    void givenExistentUsername_whenExistsById_thenReturnTrue() {
    	Publisher b = new Publisher("username","password", "name", "tin");
    	entityManager.persist(b);
        entityManager.flush();

        boolean v = publisherRepository.existsByUsername("username");
        assertEquals(true, v);
        
        entityManager.remove(b);
        entityManager.flush();
    }
    
    @Test
    void givenInexistentUsername_whenExistsById_thenReturnTrue() {
        boolean v = publisherRepository.existsByUsername("username");
        assertEquals(false, v);
    }
    
    @Test
    void givenExistentName_whenExistsById_thenReturnTrue() {
    	Publisher b = new Publisher("username","password", "name", "tin");
    	entityManager.persist(b);
        entityManager.flush();

        boolean v = publisherRepository.existsById("name");
        assertEquals(true, v);
        
        entityManager.remove(b);
        entityManager.flush();
    }
    
    @Test
    void givenInexistentName_whenExistsById_thenReturnTrue() {
        boolean v = publisherRepository.existsById("name");
        assertEquals(false, v);
    }
}

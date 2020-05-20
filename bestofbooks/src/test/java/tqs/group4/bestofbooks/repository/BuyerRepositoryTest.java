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

import tqs.group4.bestofbooks.model.Buyer;

@SpringBootTest
@Transactional
public class BuyerRepositoryTest {
	
	@Autowired
    private EntityManager entityManager;

    @Autowired
    private BuyerRepository buyerRepository;
    
    @BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, buyers, books_orders, revenues").executeUpdate();
    }
    
    @Test
    void givenExistentUsername_whenFindById_returnBuyer() {
    	Buyer b = new Buyer("username","password");
    	entityManager.persist(b);
        entityManager.flush();

        Optional<Buyer> queryResults = buyerRepository.findById("username");
        assertEquals(b, queryResults.get());
        
        entityManager.remove(b);
        entityManager.flush();
    }
    
    @Test
    void givenInexistentUsername_whenFindById_returnEmpty() {
    	Optional<Buyer> queryResults = buyerRepository.findById("username");
        assertEquals(false, queryResults.isPresent());
    }
    
    @Test
    void given3Buyers_whenFindAll_return3Buyers() {
    	Buyer b1 = new Buyer("username1","password1");
    	Buyer b2 = new Buyer("username2","password2");
    	Buyer b3 = new Buyer("username3","password3");
    	
    	entityManager.persist(b1);
    	entityManager.persist(b2);
    	entityManager.persist(b3);
        entityManager.flush();
    	
    	List<Buyer> l = buyerRepository.findAll();
    	
    	assertEquals(3, l.size());
    	assertEquals(true, l.contains(b1));
    	assertEquals(true, l.contains(b2));
    	assertEquals(true, l.contains(b3));
    }
    
    @Test
    void givenExistentUsername_whenExistsById_thenReturnTrue() {
    	Buyer b = new Buyer("username","password");
    	entityManager.persist(b);
        entityManager.flush();

        boolean v = buyerRepository.existsById("username");
        assertEquals(true, v);
        
        entityManager.remove(b);
        entityManager.flush();
    }
    
    @Test
    void givenInexistentUsername_whenExistsById_thenReturnTrue() {
    	
        boolean v = buyerRepository.existsById("username");
        assertEquals(false, v);
        
    }

}

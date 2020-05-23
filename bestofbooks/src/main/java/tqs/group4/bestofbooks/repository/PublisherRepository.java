package tqs.group4.bestofbooks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tqs.group4.bestofbooks.model.Publisher;


public interface PublisherRepository extends JpaRepository<Publisher, String>{

	Optional<Publisher> findByUsername(String username);
	
	boolean existsByUsername(String username);

	boolean existsByName(String name);

}

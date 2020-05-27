package tqs.group4.bestofbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.model.Buyer;
import tqs.group4.bestofbooks.repository.BuyerRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private BuyerRepository buyerRepository;

    public Buyer getBuyerFromUsername(String username) throws UserNotFoundException {
        Optional<Buyer> buyer = buyerRepository.findById(username);
        if (!buyer.isPresent()) {
            throw new UserNotFoundException("Buyer " + username + " was not found.");
        }
        return buyer.get();
    }
}

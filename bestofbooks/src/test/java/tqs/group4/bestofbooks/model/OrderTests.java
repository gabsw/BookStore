package tqs.group4.bestofbooks.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import tqs.group4.bestofbooks.mocks.BookMocks;

public class OrderTests {

    Order order;
    BookOrder bookOrder1, bookOrder2, bookOrder3;
    List<BookOrder> bookOrders = new ArrayList<>();

    String paymentReference1 = "EC%2d2B984685J43051234", buyerUsername = "some_buyer", isbn1="4569852123652",
        isbn2 = "7854123698523", isbn3 = "1254125412541", address = "buyer address";

    int quant1 = 2, quant2 = 3, quant3 = 1, id = 3;

    double finalPrice = 10.00;
    
    @BeforeEach
    public void setUp(){
        try {
            bookOrder1 = new BookOrder(BookMocks.infiniteJest, null, quant1);
            bookOrder2 = new BookOrder(BookMocks.onTheRoad, null, quant2);
            bookOrders.add(bookOrder1);
            bookOrders.add(bookOrder2);
            order = new Order(paymentReference1, buyerUsername, bookOrders, address, finalPrice);
            order.setId(id);
            bookOrder1.setOrder(order);
            bookOrder2.setOrder(order);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown(){
        bookOrders = new ArrayList<>();
        order = null;
        bookOrder1 = null;
        bookOrder2 = null;
        bookOrder3 = null;
    }

    @Test
    public void testEquals(){
        EqualsVerifier.forClass(order.getClass())
            .withPrefabValues(BookOrder.class, bookOrder1, bookOrder2)
            .withIgnoredFields("paymentReference", "id", "address")
            .verify();
    }

    @Test
    public void testHashCodeEqual(){
        Order newOrder = new Order(paymentReference1, buyerUsername, bookOrders, address, finalPrice);
        assertAll(
            () -> {assertNotSame(order, newOrder);},
            () -> {assertEquals(order.hashCode(), newOrder.hashCode());}
        );
    }

    @Test
    public void testHashCodeNotEqual(){
        assertAll(
            () -> {
                assertNotEquals(order.hashCode(), new Order(paymentReference1, buyerUsername + "3",
                bookOrders, address, finalPrice).hashCode());
            },
            () -> {
                List<BookOrder> newBookOrders = new ArrayList<>();
                bookOrder3 = new BookOrder(BookMocks.infiniteJest, null, quant2);
                bookOrders.add(bookOrder3);
                assertNotEquals(order.hashCode(), new Order(paymentReference1, buyerUsername,
                newBookOrders, address, finalPrice).hashCode());
            },
            () -> {assertNotEquals(order.hashCode(), new Order(paymentReference1, buyerUsername,
                bookOrders, address, finalPrice + 5).hashCode());
            }
        );
    }

    @Test
    public void testGetPaymentReference(){
        assertEquals(paymentReference1, order.getPaymentReference());
    }

    @Test
    public void testGetBuyerUsername(){
        assertEquals(buyerUsername, order.getBuyerUsername());
    }

    @Test
    public void testGetIsbn(){
        assertEquals(bookOrders, order.getBookOrders());
    }

    @Test
    public void testGetAddress(){
        assertEquals(address, order.getAddress());
    }

    @Test
    public void testSizeAfterAddBookOrder(){
        int size = order.getBookOrders().size();
        bookOrder3 = new BookOrder(BookMocks.infiniteJest, null, quant3);
        order.addBookOrder(bookOrder3);
        assertEquals(size + 1, order.getBookOrders().size());
    }

    @Test
    public void testOrderAfterAddBookOrder(){
        bookOrder3 = new BookOrder(BookMocks.onTheRoad, null, quant1);
        order.addBookOrder(bookOrder3);
        assertEquals(order, bookOrder3.getOrder());
    }

    @Test
    public void testSizeAfterRemoveBookOrder(){
        int size = order.getBookOrders().size();
        order.removeBookOrder(bookOrder1);
        assertEquals(size - 1, order.getBookOrders().size());
    }

    @Test
    public void testNullOrderAfterRemoveBookOrder(){
        order.removeBookOrder(bookOrder2);
        assertNull(bookOrder2.getOrder());
    }
}
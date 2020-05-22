package tqs.group4.bestofbooks.dto;

import org.junit.jupiter.api.Test;
import tqs.group4.bestofbooks.mocks.BookOrderMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tqs.group4.bestofbooks.dto.BookOrderDTO.fromBookOrder;

public class BookOrderDTOTests {

    private BookOrderDTO bookOrderDTO = fromBookOrder(BookOrderMocks.bookOrder1);

    @Test
    public void testGetIsbn(){
        assertEquals(BookOrderMocks.bookOrder1.getBook().getIsbn(), bookOrderDTO.getIsbn());
    }

    @Test
    public void testGetTitle(){
        assertEquals(BookOrderMocks.bookOrder1.getBook().getTitle(), bookOrderDTO.getTitle());
    }

    @Test
    public void testGetAuthor(){
        assertEquals(BookOrderMocks.bookOrder1.getBook().getAuthor(), bookOrderDTO.getAuthor());
    }

    @Test
    public void testGetQuantity(){
        assertEquals(BookOrderMocks.bookOrder1.getQuantity(), bookOrderDTO.getQuantity());
    }
}

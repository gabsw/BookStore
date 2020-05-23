package tqs.group4.bestofbooks.mocks;

import tqs.group4.bestofbooks.model.BookOrder;

public class BookOrderMocks {
    public final static BookOrder bookOrder1 = new BookOrder(BookMocks.onTheRoad, OrderMocks.order1, 2);
    public final static BookOrder bookOrder2 = new BookOrder(BookMocks.infiniteJest, OrderMocks.order1, 5);
    public final static BookOrder bookOrder3 = new BookOrder(BookMocks.infiniteJest, OrderMocks.order2, 10);
}
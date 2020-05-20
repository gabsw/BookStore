package tqs.group4.bestofbooks.mocks;

import java.util.ArrayList;
import java.util.Arrays;

import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Order;

import static tqs.group4.bestofbooks.mocks.BookOrderMocks.*;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;

public class OrderMocks {
    public final static Order order1 = new Order(
            "AC%EWRGER684654165",
            "77th st no 21, LA, CA, USA",
            10.00,
            buyer1
    );

    public final static Order order2 = new Order(
            "AC%EWRGER6848569656",
            "77th st no 21, LA, CA, USA",
            15.00,
            buyer1
    );


    static {
        OrderMocks.order1.addBookOrder(bookOrder1);
        OrderMocks.order1.addBookOrder(bookOrder2);
        OrderMocks.order2.addBookOrder(bookOrder3);
    }
}
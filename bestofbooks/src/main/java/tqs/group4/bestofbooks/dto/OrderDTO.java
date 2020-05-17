package tqs.group4.bestofbooks.dto;

import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Order;

import java.util.Collection;
import java.util.stream.Collectors;

public class OrderDTO {
    private Integer id;
    private String paymentReference;
    private String address;
    private Double finalPrice;
    private String buyerName;
    private Collection<BookOrderDTO> bookOrders;

    OrderDTO(Integer id, String paymentReference, String address, Double finalPrice, String buyerName,
                    Collection<BookOrderDTO> bookOrders) {
        this.id = id;
        this.paymentReference = paymentReference;
        this.address = address;
        this.finalPrice = finalPrice;
        this.buyerName = buyerName;
        this.bookOrders = bookOrders;
    }

    public Integer getId() {
        return id;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public String getAddress() {
        return address;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public Collection<BookOrderDTO> getBookOrders() {
        return bookOrders;
    }

    public static OrderDTO fromOrder(Order order) {
        String buyerName = order.getBuyer().getUsername();
        return new OrderDTO(
                order.getId(),
                order.getPaymentReference(),
                order.getAddress(),
                order.getFinalPrice(),
                buyerName,
                // the line below will only work correctly if called inside a @Transactional
                order.getBookOrders().stream().map(BookOrderDTO::fromBookOrder).collect(Collectors.toList())
        );
    }
}

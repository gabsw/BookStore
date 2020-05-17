package tqs.group4.bestofbooks.dto;

import tqs.group4.bestofbooks.model.Revenue;

public class RevenueDTO {
    private Integer id;
    private double amount;
    private String publisherName;
    private Integer orderId;
    private String isbn;

    public RevenueDTO(Integer id, double amount, String publisherName, Integer orderId, String isbn) {
        this.id = id;
        this.amount = amount;
        this.publisherName = publisherName;
        this.orderId = orderId;
        this.isbn = isbn;
    }

    public Integer getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getIsbn() {
        return isbn;
    }

    public static RevenueDTO fromRevenue(Revenue revenue) {
        return new RevenueDTO(revenue.getId(), revenue.getAmount(), revenue.getPublisherName(),
                revenue.getBookOrder().getId(), revenue.getBookOrder().getBook().getIsbn());
    }
}

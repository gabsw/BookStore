package tqs.group4.bestofbooks.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import tqs.group4.bestofbooks.model.BookOrder;

public class RequestOrderDTO {
    @NotEmpty(message = "Isbn list cannot be empty.")
    private List<BookOrder> content;
   
    @Size(min = 1, max = 20, message = "Buyer's username must be between 1 and 20 characters")
    @NotBlank(message = "Buyer's username cannot be null or whitespace")
    private String buyerUsername;
   
    @Size(min = 1, max = 20, message = "Payment reference must be between 1 and 20 characters")
    @NotBlank(message = "Payment reference cannot be null or whitespace")
    private String paymentReference;
   
    @Size(min = 1, max = 100, message = "Address must be between 1 and 100 characters")
    @NotBlank(message = "Address cannot be null or whitespace")
    private String address;

    public RequestOrderDTO() {
    }

    public RequestOrderDTO(List<BookOrder> content,  String buyerUsername, String paymentReference, String address) {
        this.content = content;
        this.buyerUsername = buyerUsername;
        this.paymentReference = paymentReference;
        this.address = address;
    }

    public List<BookOrder> getContent(){
        return content;
    }

    public String getBuyerUsername(){
        return buyerUsername;
    }

    public String getPaymentReference(){
        return paymentReference;
    }

    public String getAddress(){
        return address;
    }
}
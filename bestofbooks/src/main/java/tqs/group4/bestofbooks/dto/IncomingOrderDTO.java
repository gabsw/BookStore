package tqs.group4.bestofbooks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class IncomingOrderDTO {
    @NotEmpty(message = "bookOrders cannot be empty.")
    @JsonProperty("bookOrders")
    @Valid
    private List<IncomingBookOrderDTO> incomingBookOrderDTOS;
   
    @Size(min = 1, max = 20, message = "Buyer's username must be between 1 and 20 characters")
    @NotBlank(message = "Buyer's username cannot be null or whitespace")
    private String buyerUsername;
   
    @Size(min = 1, max = 20, message = "Payment reference must be between 1 and 20 characters")
    @NotBlank(message = "Payment reference cannot be null or whitespace")
    private String paymentReference;
   
    @Size(min = 1, max = 100, message = "Address must be between 1 and 100 characters")
    @NotBlank(message = "Address cannot be null or whitespace")
    private String address;

    public IncomingOrderDTO() {
    }

    public IncomingOrderDTO(List<IncomingBookOrderDTO> incomingBookOrderDTOS, String buyerUsername,
                            String paymentReference, String address) {
        this.incomingBookOrderDTOS = incomingBookOrderDTOS;
        this.buyerUsername = buyerUsername;
        this.paymentReference = paymentReference;
        this.address = address;
    }

    public List<IncomingBookOrderDTO> getIncomingBookOrderDTOS() {
        return incomingBookOrderDTOS;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomingOrderDTO that = (IncomingOrderDTO) o;
        return Objects.equals(incomingBookOrderDTOS, that.incomingBookOrderDTOS) &&
                Objects.equals(buyerUsername, that.buyerUsername) &&
                Objects.equals(paymentReference, that.paymentReference) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incomingBookOrderDTOS, buyerUsername, paymentReference, address);
    }
}
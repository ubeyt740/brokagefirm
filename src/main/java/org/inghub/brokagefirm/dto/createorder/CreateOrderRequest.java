package org.inghub.brokagefirm.dto.createorder;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.inghub.brokagefirm.model.OrderSide;

public class CreateOrderRequest {
    private Long customerId;
    private String assetName;
    @Enumerated(EnumType.STRING)
    private OrderSide side;
    private Double size;
    private Double price;

    // getter-setter
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    public OrderSide getSide() { return side; }
    public void setSide(OrderSide side) { this.side = side; }

    public Double getSize() { return size; }
    public void setSize(Double size) { this.size = size; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}

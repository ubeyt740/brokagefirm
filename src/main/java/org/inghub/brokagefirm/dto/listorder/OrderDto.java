package org.inghub.brokagefirm.dto.listorder;

import java.time.LocalDateTime;

public class OrderDto {
    private Long orderId;
    private Long customerId;
    private String assetName;
    private String side;
    private double size;
    private double price;
    private String status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Long getOrderId() {return orderId;}
    public void setOrderId(Long orderId) {this.orderId = orderId;}
    public Long getCustomerId() {return customerId;}
    public void setCustomerId(Long customerId) {this.customerId = customerId;}
    public String getAssetName() {return assetName;}
    public void setAssetName(String assetName) {this.assetName = assetName;}
    public String getSide() {return side;}
    public void setSide(String side) {this.side = side;}
    public double getSize() {return size;}
    public void setSize(double size) {this.size = size;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public LocalDateTime getCreateDate() {return createDate;}
    public void setCreateDate(LocalDateTime createDate) {this.createDate = createDate;}
    public LocalDateTime getUpdateDate() {return updateDate;}
    public void setUpdateDate(LocalDateTime updateDate) {this.updateDate = updateDate;}

}

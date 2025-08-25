package org.inghub.brokagefirm.dto.listorder;

import java.util.List;

public class ListOrderResponse {
    private boolean success;
    private String message;
    private List<OrderDto> orders;

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<OrderDto> getOrders() { return orders; }
    public void setOrders(List<OrderDto> orders) { this.orders = orders; }
}

package org.inghub.brokagefirm.dto.createorder;

public class CreateOrderResponse {
    private boolean isSuccess;
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

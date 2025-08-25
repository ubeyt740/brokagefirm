package org.inghub.brokagefirm.dto.adminorder;

public class AdminApprovalResponse {
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

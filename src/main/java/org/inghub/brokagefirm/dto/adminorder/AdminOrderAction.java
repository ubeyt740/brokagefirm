package org.inghub.brokagefirm.dto.adminorder;

public class AdminOrderAction {
    private Long orderId;
    private ApprovalDecision decision;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public ApprovalDecision getDecision() { return decision; }
    public void setDecision(ApprovalDecision decision) { this.decision = decision; }
}

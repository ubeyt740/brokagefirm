package org.inghub.brokagefirm.dto.adminorder;

import java.util.List;

public class AdminApprovalRequest {
    private List<AdminOrderAction> actions;

    public List<AdminOrderAction> getActions() { return actions; }
    public void setActions(List<AdminOrderAction> actions) { this.actions = actions; }
}

package org.inghub.brokagefirm.dto.listorder;

import java.time.LocalDateTime;

public class ListOrderRequest {
    private Long customerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Getter / Setter
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
}
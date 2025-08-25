package org.inghub.brokagefirm.dto.listasset;

public class ListAssetsRequest {
    private Long customerId;
    private String assetName;
    private Double  minSize;
    private Double  maxSize;
    private Double  minUsable;
    private Double  maxUsable;
    private Boolean onlyUsable;


    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    public Double  getMinSize() { return minSize; }
    public void setMinSize(Double  minSize) { this.minSize = minSize; }

    public Double  getMaxSize() { return maxSize; }
    public void setMaxSize(Double  maxSize) { this.maxSize = maxSize; }

    public Double  getMinUsable() { return minUsable; }
    public void setMinUsable(Double  minUsable) { this.minUsable = minUsable; }

    public Double  getMaxUsable() { return maxUsable; }
    public void setMaxUsable(Double  maxUsable) { this.maxUsable = maxUsable; }

    public Boolean getOnlyUsable() { return onlyUsable; }
    public void setOnlyUsable(Boolean onlyUsable) { this.onlyUsable = onlyUsable; }
}
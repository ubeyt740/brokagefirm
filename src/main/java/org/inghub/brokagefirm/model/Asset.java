package org.inghub.brokagefirm.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String assetName;

    private Double size;
    private Double usableSize;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    public Double getSize() { return size; }
    public void setSize(Double size) { this.size = size; }

    public Double getUsableSize() { return usableSize; }
    public void setUsableSize(Double usableSize) { this.usableSize = usableSize; }
}

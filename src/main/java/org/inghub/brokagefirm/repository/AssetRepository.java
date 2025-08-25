package org.inghub.brokagefirm.repository;

import org.inghub.brokagefirm.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByCustomerIdAndAssetName(Long customerId, String assetName);
}


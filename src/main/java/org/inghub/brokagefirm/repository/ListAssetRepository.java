package org.inghub.brokagefirm.repository;

import org.inghub.brokagefirm.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListAssetRepository extends JpaRepository<Asset, Long> {
//The reason used Query is to be able to use select and filtering in a single query.
    @Query("SELECT a FROM Asset a " +
            "WHERE (:customerId IS NULL OR a.customerId = :customerId) " +
            "AND (:assetName IS NULL OR LOWER(a.assetName) = LOWER(:assetName)) " +
            "AND (:onlyUsable IS NULL OR (:onlyUsable = TRUE AND a.usableSize > 0)) " +
            "AND (:minSize IS NULL OR a.size >= :minSize) " +
            "AND (:maxSize IS NULL OR a.size <= :maxSize) " +
            "AND (:minUsable IS NULL OR a.usableSize >= :minUsable) " +
            "AND (:maxUsable IS NULL OR a.usableSize <= :maxUsable)")
    List<Asset> findAssetsWithFilters(
            @Param("customerId") Long customerId,
            @Param("assetName") String assetName,
            @Param("onlyUsable") Boolean onlyUsable,
            @Param("minSize") Double minSize,
            @Param("maxSize") Double maxSize,
            @Param("minUsable") Double minUsable,
            @Param("maxUsable") Double maxUsable
    );
}
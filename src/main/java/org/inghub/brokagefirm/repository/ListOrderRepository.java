package org.inghub.brokagefirm.repository;

import org.inghub.brokagefirm.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ListOrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o " +
            "WHERE (:customerId IS NULL OR o.customerId = :customerId) " +
            "AND (:startDate IS NULL OR o.createDate >= :startDate) " +
            "AND (:endDate IS NULL OR o.createDate <= :endDate)")
    List<Order> findOrders(
            @Param("customerId") Long customerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}

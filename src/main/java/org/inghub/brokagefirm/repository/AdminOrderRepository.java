package org.inghub.brokagefirm.repository;

import org.inghub.brokagefirm.model.Order;
import org.inghub.brokagefirm.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByIdInAndStatus(List<Long> ids, OrderStatus status);
}
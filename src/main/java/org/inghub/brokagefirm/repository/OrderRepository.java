package org.inghub.brokagefirm.repository;

import org.inghub.brokagefirm.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

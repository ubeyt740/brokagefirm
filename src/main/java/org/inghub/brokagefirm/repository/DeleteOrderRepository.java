package org.inghub.brokagefirm.repository;

import org.inghub.brokagefirm.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeleteOrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

}

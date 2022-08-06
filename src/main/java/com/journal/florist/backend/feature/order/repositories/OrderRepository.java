package com.journal.florist.backend.feature.order.repositories;

import com.journal.florist.backend.feature.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


}
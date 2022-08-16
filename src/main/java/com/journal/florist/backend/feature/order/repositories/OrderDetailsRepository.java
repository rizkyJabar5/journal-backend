package com.journal.florist.backend.feature.order.repositories;

import com.journal.florist.backend.feature.order.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {



}

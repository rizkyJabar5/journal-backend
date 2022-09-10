package com.journal.florist.backend.feature.order.repositories;

import com.journal.florist.backend.feature.order.model.OrderShipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderShipmentsRepository extends JpaRepository<OrderShipments, Long> {

    @Query("select o from OrderShipments o where o.order.publicKey = ?1")
    OrderShipments findByOrderPublicKey(String orderId);
}

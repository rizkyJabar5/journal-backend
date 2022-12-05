package com.journal.florist.backend.feature.order.repositories;

import com.journal.florist.backend.feature.order.model.DeliveryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, Long> {

    @Query("select (count(d) > 0) " +
            "from DeliveryNote d " +
            "where d.order.publicKey = ?1 " +
            "and d.isPrinted = true")
    boolean orderPrinted(String orderId);
    @Query("""
            select (count(d) > 0)
            from DeliveryNote d
            where d.order.publicKey = ?1
            and d.order.orderStatus = com.journal.florist.backend.feature.order.enums.OrderStatus.SENT
            """)
    boolean orderWithStatusSent(String orderId);

    @Query("select (count(d) > 0) from DeliveryNote d where d.gnrId = ?1")
    boolean existsByGnrId(String gnrId);
}

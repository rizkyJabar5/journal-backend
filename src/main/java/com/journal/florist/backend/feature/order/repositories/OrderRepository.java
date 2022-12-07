package com.journal.florist.backend.feature.order.repositories;

import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.order.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    Orders findOrderByPublicKey(String publicKey);

    @Query("""
            select o from Orders o
            where cast(o.createdAt as date) = cast(:orderDate as date)
            and o.orderStatus = :orderStatus
            and o.paymentStatus = :paymentStatus
            order by o.createdAt desc
            """)
    Page<Orders> findByField(@Param("orderDate") Date orderDate,
                             @Param("orderStatus") OrderStatus orderStatus,
                             @Param("paymentStatus") PaymentStatus paymentStatus,
                             Pageable pageable);

    @Query("""
            select o
            from Orders o
            where cast(o.createdAt as date) = cast(?1 as date)
            order by o.createdAt desc
            """)
    List<Orders> findOrderByDate(Date orderDate);

    @Query("""
            select o
            from Orders o
            order by o.createdAt desc
            """)
    Page<Orders> findAllOrder(Pageable pageable);

    @Query("""
            select o
            from Orders o
            where o.paymentStatus = com.journal.florist.backend.feature.order.enums.PaymentStatus.PAID_OFF
            order by o.createdAt desc
            """)
    Page<Orders> findOrderPaidOff(Pageable pageable);

    List<Orders> findOrderByOrderStatus(OrderStatus orderStatus);

    @Query("select o " +
            "from Orders o " +
            "where o.paymentStatus = ?1 " +
            "order by o.createdAt desc")
    List<Orders> findOrderPaymentStatus(PaymentStatus paymentStatus);

    @Query("select o " +
            "from Orders o " +
            "where upper(o.customer.name) like upper(concat('%', ?1, '%')) " +
            "order by o.createdAt desc")
    List<Orders> findOrderByCustomerName(String customerName);
}
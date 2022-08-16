/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.order.model;

import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.product.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
public class HistoryOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apps_sequence")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "The id of the entity is required")
    private String historyOrderCode;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            targetEntity = Orders.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "order_fk_id"))
    private Orders orders;

    @ManyToOne(
            fetch = FetchType.EAGER,
            targetEntity = Customers.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "customer_fk_id"))
    private Customers customers;

    @ManyToOne(
            fetch = FetchType.EAGER,
            targetEntity = Product.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "buy_product_fk_id"))
    private Product product;

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HistoryOrders other)) {
            return false; // null or other class
        }

        if (getHistoryOrderCode() != null) {
            return getId().equals(other.getId());
        }
        return super.equals(other);
    }

    @PrePersist
    private void prePersist() {
            if(Objects.isNull(getHistoryOrderCode())){
                setHistoryOrderCode(UUID.randomUUID().toString().replaceAll("-", ""));
            }
    }

}

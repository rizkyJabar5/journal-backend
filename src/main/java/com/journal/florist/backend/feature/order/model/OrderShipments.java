package com.journal.florist.backend.feature.order.model;

import com.journal.florist.backend.feature.utils.Address;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderShipments extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_id",
            foreignKey = @ForeignKey(name = "order_fk_id"),
            nullable = false)
    private Orders order;

    private String recipientName;

    @Embedded
    private Address deliveryAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
}

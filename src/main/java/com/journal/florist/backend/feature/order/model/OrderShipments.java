package com.journal.florist.backend.feature.order.model;

import com.journal.florist.backend.feature.utils.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderShipments implements Serializable {

    @Id
    private Long orderShipmentId;

    @OneToOne
    @MapsId
    private Orders order;

    private String recipientName;

    @Embedded
    private Address deliveryAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    public OrderShipments(Orders order, String recipientName, Address deliveryAddress, Date deliveryDate) {
        this.order = order;
        this.recipientName = recipientName;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderShipments shipments) || !super.equals(o)) return false;
        return Objects.equals(getOrder(), shipments.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOrder());
    }
}

package com.journal.florist.backend.feature.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.journal.florist.backend.feature.product.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class OrderProductPK implements Serializable {

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Orders order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductPK pk) || !super.equals(o)) return false;
        return Objects.equals(getProduct(), pk.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOrder(), getProduct());
    }
}

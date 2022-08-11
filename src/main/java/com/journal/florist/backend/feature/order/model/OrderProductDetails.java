package com.journal.florist.backend.feature.order.model;

import com.journal.florist.backend.feature.product.model.Product;
import com.journal.florist.backend.feature.utils.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderProductDetails extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "order_fk_id"))
    private Orders order;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = Product.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "product_fk_id"))
    private Product product;

    @Lob
    private String notes;
    private Integer quantity;
    private BigInteger amount;

    public BigInteger getTotalAmount() {
        return this.amount = this.product.getPrice().multiply(BigInteger.valueOf(this.quantity));
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof OrderProductDetails orders) || !super.equals(o)) return false;
        return Objects.equals(getProduct(), orders.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProduct(), getOrder());
    }
}

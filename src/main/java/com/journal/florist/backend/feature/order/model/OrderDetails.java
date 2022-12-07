package com.journal.florist.backend.feature.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.journal.florist.backend.feature.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetails implements Serializable {

    @EmbeddedId
    @JsonIgnore
    private OrderProductPK pk;

    @Lob
    private String notes;

    @Column(nullable = false)
    @Min(value = 1L, message = "Minimal quantity should be at 1")
    private Integer quantity;

    private BigDecimal costPrice;
    private BigDecimal price;

    @Transient
    public Product getProduct() {
        return this.pk.getProduct();
    }

    public BigDecimal getTotalPrice() {
        this.price = getProduct().getPrice();
        return this.price.multiply(BigDecimal.valueOf(getQuantity()));
    }

    public BigDecimal getTotalCostPrice() {
        this.costPrice = getProduct().getCostPrice();
        return this.costPrice.multiply(BigDecimal.valueOf(getQuantity()));
    }

    public BigDecimal getNetPrice() {
        return getTotalPrice().subtract(getTotalCostPrice());
    }

    public OrderDetails(Orders order,
                        Product product,
                        String notes,
                        Integer quantity,
                        BigDecimal costPrice,
                        BigDecimal price) {
        pk = new OrderProductPK();
        pk.setOrder(order);
        pk.setProduct(product);
        this.notes = notes;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetails orders) || !super.equals(o)) return false;
        return Objects.equals(getPk(), orders.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPk());
    }

}

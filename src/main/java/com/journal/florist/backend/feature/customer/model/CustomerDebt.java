package com.journal.florist.backend.feature.customer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_debt")
public class CustomerDebt {

    @Id
    @Column(name = "customer_id")
    private Long customerID;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    private Customers customer;

    @Column(name = "total_debt")
    private BigDecimal totalDebt = BigDecimal.ZERO;
}

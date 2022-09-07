package com.journal.florist.backend.feature.ledger.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "finance")
public class Finance {

    @Id
    private String financeId;

    @Temporal(TemporalType.TIMESTAMP)
    Date fromDaysFinance;

    @Temporal(TemporalType.DATE)
    Date monthlyFinance;

    @Temporal(TemporalType.DATE)
    Date yearlyFinance;

    BigDecimal expense = BigDecimal.ZERO;
    BigDecimal revenue = BigDecimal.ZERO;
    BigDecimal accountReceivable = BigDecimal.ZERO;
    BigDecimal totalDebt = BigDecimal.ZERO;

    @PrePersist
    private void generatorId() {
        if(financeId != null) {
            return;
        }
        setFinanceId(RandomStringUtils.randomAlphanumeric(10));
    }

}

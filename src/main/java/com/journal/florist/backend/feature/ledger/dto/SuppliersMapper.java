package com.journal.florist.backend.feature.ledger.dto;

import com.journal.florist.app.common.utils.converter.DateConverter;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class SuppliersMapper {
    private String id;
    private String supplierName;
    private BigDecimal totalDebt;
    private BigDecimal amountPayDebt;
    private String payDebtDate;

    public SuppliersMapper buildSuppliersResponse(Suppliers supplier) {
        BigDecimal supplierAmountPay = supplier.getAmountPay();
        Date supplierPaydebtDate = supplier.getPayDebtDate();
        String payDate = "" ;
        BigDecimal amount = BigDecimal.ZERO;

        if (supplierPaydebtDate != null) {
            var dateTime = DateConverter.toLocalDateTime(supplier.getPayDebtDate());
            payDate = DateConverter.formatDateTime().format(dateTime);
        }

        if (supplierAmountPay != null) {
            amount = supplier.getAmountPay();
        }

        return SuppliersMapper.builder()
                .id(supplier.getId())
                .supplierName(supplier.getSupplierName())
                .totalDebt(supplier.getTotalDebt())
                .amountPayDebt(amount)
                .payDebtDate(payDate)
                .build();
    }
}

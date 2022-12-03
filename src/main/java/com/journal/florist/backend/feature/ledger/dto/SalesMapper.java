package com.journal.florist.backend.feature.ledger.dto;

import com.journal.florist.app.common.utils.converter.DateConverter;
import com.journal.florist.backend.feature.ledger.model.Sales;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class SalesMapper implements Serializable {

    private String saleToday;
    private BigDecimal monthlySales;
    private BigDecimal saleAmount;
    private BigDecimal netProfit;
    private String orderId;
    private BigDecimal totalOrderAmount;
    private String customerName;
    private String phoneNumber;
    private String companyName;

    public SalesMapper buildSalesResponse(Sales sales) {
        LocalDateTime dateTime = DateConverter.toLocalDateTime(sales.getSaleDate());
        String saleDate = DateConverter.formatDateTime().format(dateTime);

        return SalesMapper.builder()
                .saleToday(saleDate)
                .monthlySales(sales.getMonthlySale())
                .saleAmount(sales.getSalesAmount())
                .netProfit(sales.getNetProfit())
                .orderId(sales.getOrders().getPublicKey())
                .totalOrderAmount(sales.getTotalOrderAmount())
                .customerName(sales.getCustomers().getName())
                .phoneNumber(sales.getCustomers().getPhoneNumber())
                .companyName(sales.getCustomers().getCompany().getCompanyName())
                .build();
    }
}

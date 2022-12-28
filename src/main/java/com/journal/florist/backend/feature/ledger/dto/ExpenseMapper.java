package com.journal.florist.backend.feature.ledger.dto;

import com.journal.florist.app.common.utils.converter.DateConverter;
import com.journal.florist.backend.feature.ledger.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ExpenseMapper implements Serializable {
    private String expenseId;
    private String date;
    private String createdBy;
    private String additionalInfo;
    private BigDecimal amount;
    private String payFor;
    private  String supplierName;
    private String lastModifiedBy;
    private String lastModifiedDate;


    public ExpenseMapper buildExpenseResponse(Expense expense) {

        var dateTime = DateConverter.toLocalDateTime(expense.getCreatedAt());
        String addedDate = DateConverter.formatDate().format(dateTime);

        String modifiedDate = null;

        String modifiedBy = expense.getLastModifiedBy();

        if (expense.getLastModifiedDate() != null) {
            var toLocalDate = DateConverter.toLocalDateTime(expense.getLastModifiedDate());
            modifiedDate = DateConverter.formatDate().format(toLocalDate);
        }

        if (modifiedBy == null) {
            lastModifiedBy = null;
        }

        String name = expense.getSupplierName() == null ? "" : expense.getSupplierName();
        return ExpenseMapper.builder()
                .expenseId(expense.getPublicKey())
                .date(addedDate)
                .createdBy(expense.getCreatedBy())
                .additionalInfo(expense.getAdditionalInformation())
                .amount(expense.getAmount())
                .payFor(expense.getPayFor().getName())
                .supplierName(name)
                .lastModifiedBy(lastModifiedBy)
                .lastModifiedDate(modifiedDate)
                .build();
    }
}

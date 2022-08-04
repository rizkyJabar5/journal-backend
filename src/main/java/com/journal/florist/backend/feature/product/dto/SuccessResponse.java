package com.journal.florist.backend.feature.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {

    private String message;
    private StatusOperation status;
    public enum StatusOperation {
        FAILED("Failed"),
        PENDING("Pending"),
        SUCCESS("Success"),
        SKIPPED("Skipped"),
        UNKNOWN("Unknown");

        public final String s;
        StatusOperation(String s) {
            this.s = s;
        }
    }
}

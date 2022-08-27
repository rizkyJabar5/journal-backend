/*
 * Copyright (c) 2022.
 */

package com.journal.florist.app.common.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BaseResponse {

    private HttpStatus httpStatus;
    private String message;
    private Object data;

    public BaseResponse(Object data) {
        this.data = data;
    }
}

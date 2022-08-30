/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.product.dto.CategoryRequest;
import com.journal.florist.backend.feature.product.model.Category;

public interface CategoryService extends HasLogger {

    Category findByCategoryKey(String categoryKey);
    BaseResponse findAllCategory();
    BaseResponse addNewCategory(CategoryRequest category);
    BaseResponse updateCategory(CategoryRequest category);
    SuccessResponse deleteCategory(String categoryKey);
}

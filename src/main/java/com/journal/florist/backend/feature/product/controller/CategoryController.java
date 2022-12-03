package com.journal.florist.backend.feature.product.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.backend.feature.product.dto.category.AddCategoryRequest;
import com.journal.florist.backend.feature.product.dto.category.CategoryRequest;
import com.journal.florist.backend.feature.product.model.Category;
import com.journal.florist.backend.feature.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import static com.journal.florist.app.constant.ApiUrlConstant.CATEGORY_URL;

@Tag(name = "Category Endpoint",
        description = "Api for transaction category product ")
@RequiredArgsConstructor
@RestController
@RequestMapping(CATEGORY_URL)
@RolesAllowed({"superadmin", "cashier"})
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Fetching all categories")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<BaseResponse> getAllCategories() {
        BaseResponse response = categoryService.findAllCategory();
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Filter by category key")
    @GetMapping(value = "/")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<BaseResponse> getCategoryById(@RequestParam(name = "categoryId") String categoryId) {
        Category category = categoryService.findByCategoryId(categoryId);

        BaseResponse response = new BaseResponse(
                HttpStatus.OK,
                String.format("Customer found with id %s", categoryId),
                category
        );

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Add new category")
    @PostMapping(value = "/add-category")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<BaseResponse> addCategory(@Valid @RequestBody AddCategoryRequest request) {
        BaseResponse response = categoryService.addNewCategory(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update category")
    @PutMapping(value = "/update-category")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<BaseResponse> updateCategory(@Valid @RequestBody CategoryRequest category) {

        BaseResponse response = categoryService.updateCategory(category);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete category by key")
    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<SuccessResponse> deleteCategory(@RequestParam(name = "id") String categoryKey) {
        SuccessResponse response = categoryService.deleteCategory(categoryKey);
        return ResponseEntity.ok().body(response);
    }
}

package com.journal.florist.backend.feature.product.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.backend.feature.product.dto.CategoryRequest;
import com.journal.florist.backend.feature.product.model.Category;
import com.journal.florist.backend.feature.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @GetMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> getAllCategories() {
        BaseResponse response = categoryService.findAllCategory();
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Filter by category key")
    @GetMapping(value = "/{categoryKey}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategoryByKey(@PathVariable("categoryKey") String categoryKey) {
        Category response = categoryService.findByCategoryKey(categoryKey);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Add new category")
    @PostMapping(value = "/add-category",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> addCategory(@Valid @RequestBody CategoryRequest category) {
        BaseResponse response = categoryService.addNewCategory(category);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update category")
    @PutMapping(value = "/update-category",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> updateCategory(@Valid @RequestBody CategoryRequest category) {

        BaseResponse response = categoryService.updateCategory(category);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete category by key")
    @DeleteMapping(value = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> deleteCategory(@RequestParam(name = "key") String categoryKey) {
        SuccessResponse response = categoryService.deleteCategory(categoryKey);
        return ResponseEntity.ok().body(response);
    }
}

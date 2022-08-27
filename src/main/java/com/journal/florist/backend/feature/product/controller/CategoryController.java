package com.journal.florist.backend.feature.product.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.backend.feature.product.model.Category;
import com.journal.florist.backend.feature.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static com.journal.florist.app.constant.ApiUrlConstant.CATEGORY_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(CATEGORY_URL)
@RolesAllowed({"superadmin", "cashier"})
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<BaseResponse> getAllCategories() {
        BaseResponse response = categoryService.findAllCategory();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{categoryKey}")
    public ResponseEntity<Category> getCategoryByKey(@PathVariable("categoryKey") String categoryKey) {
        Category response = categoryService.findByCategoryKey(categoryKey);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/add-category")
    public ResponseEntity<BaseResponse> addCategory(Category category) {
        BaseResponse response = categoryService.addNewCategory(category);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update-category")
    public ResponseEntity<BaseResponse> updateCategory(Category request) {

        BaseResponse response = categoryService.updateCategory(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse> deleteCategory(@RequestParam(name = "key") String categoryKey) {
        SuccessResponse response = categoryService.deleteCategory(categoryKey);
        return ResponseEntity.ok().body(response);
    }
}

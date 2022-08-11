/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.service;

import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.app.utils.BaseResponse;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.app.utils.SuccessResponse;
import com.journal.florist.app.utils.SuccessResponse.StatusOperation;
import com.journal.florist.backend.feature.product.model.Category;
import com.journal.florist.backend.feature.product.repositories.CategoryRepository;
import com.journal.florist.backend.feature.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.journal.florist.app.constant.JournalConstants.*;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findByCategoryKey(String categoryKey) {
        return categoryRepository.findByPublicKey(categoryKey)
                .orElseThrow(() -> new NotFoundException(String.format(CATEGORY_NOT_FOUND_MSG, categoryKey)));
    }

    @Override
    public BaseResponse findAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()) {
            return new BaseResponse(
                    HttpStatus.NO_CONTENT,
                    String. format(NO_CONTENT, EntityUtil.getName(Category.class)),
                    null
            );
        }
        return new BaseResponse(
                HttpStatus.FOUND,
                "We found all categories.",
                categories
        );
    }

    @Override
    public BaseResponse addNewCategory(Category category) {
        Category entity = new Category();
        Authentication authentication = SecurityUtils.getAuthentication();
        String createdBy = authentication.getName();
        boolean authenticated = SecurityUtils.isAuthenticated();

        if (authenticated) {
            boolean persisted = categoryRepository.existsByNameCategory(category.getNameCategory());
            if (persisted) {
                throw new IllegalException(String.format(MUST_BE_UNIQUE, EntityUtil.getName(Category.class)));
            }
            entity.setNameCategory(category.getNameCategory());
            entity.setCreatedBy(createdBy);
            entity.setCreatedAt(new Date(System.currentTimeMillis()));
            entity.setDescription(category.getDescription());

        }

        categoryRepository.save(entity);
        getLogger().info("Adding category: {}", entity.getPublicKey());
        return new BaseResponse(
                HttpStatus.CREATED,
                String.format(SUCCESSFULLY_ADD_NEW_ENTITY, EntityUtil.getName(Category.class)),
                entity);
    }

    @Override
    public BaseResponse updateCategory(Category category) {

        Authentication authentication = SecurityUtils.getAuthentication();
        String updatedBy = authentication.getName();
        boolean authenticated = SecurityUtils.isAuthenticated();
        Category persisted = findByCategoryKey(category.getPublicKey());
        if (authenticated) {
            boolean isEqual = categoryRepository.existsByNameCategory(category.getNameCategory());
            if(isEqual) {
                throw new IllegalException(MUST_BE_UNIQUE + EntityUtil.getName(Category.class));
            }
            if (category.getNameCategory() != null) {
                persisted.setNameCategory(category.getNameCategory());
            }

            if(category.getDescription() != null) {
                persisted.setDescription(category.getDescription());
            }

            persisted.setLastModifiedBy(updatedBy);
            persisted.setLastModifiedDate(new Date(System.currentTimeMillis()));
        }

        categoryRepository.save(persisted);
        getLogger().info("Adding new product {}", persisted.getPublicKey());
        return new BaseResponse(
                HttpStatus.ACCEPTED,
                "Successfully updated category " + category.getPublicKey(),
                persisted
        );
    }

    @Override
    public SuccessResponse deleteCategory(String categoryKey) {
        Category persisted = findByCategoryKey(categoryKey);
        categoryRepository.delete(persisted);
        getLogger().info("Successfully deleted category {}", persisted.getPublicKey());
        return new SuccessResponse(
                String.format(SUCCESSFULLY_DELETE_OPERATION, persisted.getPublicKey()),
                StatusOperation.SUCCESS
        );
    }
}

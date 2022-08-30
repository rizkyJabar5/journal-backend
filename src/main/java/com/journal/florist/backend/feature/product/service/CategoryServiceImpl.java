/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.app.common.messages.SuccessResponse.StatusOperation;
import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.product.dto.CategoryRequest;
import com.journal.florist.backend.feature.product.model.Category;
import com.journal.florist.backend.feature.product.repositories.CategoryRepository;
import com.journal.florist.backend.feature.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, EntityUtil.getName(Category.class), categoryKey))
                );
    }

    @Override
    public BaseResponse findAllCategory() {
        List<Category> categories = categoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Category::getNameCategory))
                .toList();
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
    public BaseResponse addNewCategory(CategoryRequest category) {
        Category entity = new Category();

        Authentication authentication = SecurityUtils.getAuthentication();
        String createdBy = authentication.getName();
        boolean authenticated = SecurityUtils.isAuthenticated();

        if (authenticated) {
            boolean persisted = categoryRepository.existsByNameCategory(category.getCategoryName());
            if (persisted) {
                throw new IllegalException(String.format(MUST_BE_UNIQUE, EntityUtil.getName(Category.class)));
            }

            entity.setPublicKey(category.getCategoryId());
            entity.setNameCategory(category.getCategoryName());
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
    public BaseResponse updateCategory(CategoryRequest category) {

        Authentication authentication = SecurityUtils.getAuthentication();
        String updatedBy = authentication.getName();
        boolean authenticated = SecurityUtils.isAuthenticated();

        if(category.getCategoryId().isEmpty()) {
            throw new IllegalException("Category id is required");
        }

        Category persisted = findByCategoryKey(category.getCategoryId());
        if (authenticated) {
            boolean isEqual = categoryRepository.existsByNameCategory(category.getCategoryName());
            if(isEqual) {
                throw new IllegalException(
                        String.format(MUST_BE_UNIQUE, EntityUtil.getName(Category.class))
                );
            }
            if (category.getCategoryName() != null) {
                persisted.setNameCategory(category.getCategoryName());
            }

            if(category.getDescription() != null && !category.getDescription().isBlank()) {
                persisted.setDescription(category.getDescription());
            }

            persisted.setLastModifiedBy(updatedBy);
            persisted.setLastModifiedDate(new Date(System.currentTimeMillis()));
        }

        categoryRepository.save(persisted);
        getLogger().info("Update category id {}", persisted.getPublicKey());
        return new BaseResponse(
                HttpStatus.ACCEPTED,
                "Successfully updated category " + category.getCategoryId(),
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

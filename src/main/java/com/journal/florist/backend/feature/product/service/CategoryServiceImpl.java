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
import com.journal.florist.backend.feature.product.dto.category.AddCategoryRequest;
import com.journal.florist.backend.feature.product.dto.category.CategoryMapper;
import com.journal.florist.backend.feature.product.dto.category.CategoryRequest;
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
    private final CategoryMapper categoryMapper;

    @Override
    public Category findByCategoryId(String categoryId) {
        return categoryRepository.findByPublicKey(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, EntityUtil.getName(Category.class), categoryId))
                );
    }

    @Override
    public BaseResponse findAllCategory() {
        List<CategoryMapper> categories = categoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Category::getNameCategory))
                .map(categoryMapper::categoryMapper)
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
    public BaseResponse addNewCategory(AddCategoryRequest request) {
        Category entity = new Category();

        Authentication authentication = SecurityUtils.getAuthentication();
        String createdBy = authentication.getName();
        boolean authenticated = SecurityUtils.isAuthenticated();

        if (authenticated) {
            boolean persisted = categoryRepository.existsByNameCategory(request.categoryName());
            if (persisted) {
                throw new IllegalException(String.format(MUST_BE_UNIQUE, EntityUtil.getName(Category.class)));
            }

            entity.setNameCategory(request.categoryName());
            entity.setCreatedBy(createdBy);
            entity.setCreatedAt(new Date(System.currentTimeMillis()));
            entity.setDescription(request.description());
        }

        categoryRepository.save(entity);
        getLogger().info("Adding category: {}", entity.getPublicKey());
        categoryMapper.categoryMapper(entity);

        return new BaseResponse(
                HttpStatus.CREATED,
                String.format(SUCCESSFULLY_ADD_NEW_ENTITY, EntityUtil.getName(Category.class)),
                categoryMapper.categoryMapper(entity));
    }

    @Override
    public BaseResponse updateCategory(CategoryRequest category) {

        Authentication authentication = SecurityUtils.getAuthentication();
        String updatedBy = authentication.getName();
        boolean authenticated = SecurityUtils.isAuthenticated();

        if(category.getCategoryId().isEmpty()) {
            throw new IllegalException("Category id is required");
        }

        Category persisted = findByCategoryId(category.getCategoryId());
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
                categoryMapper.categoryMapper(persisted)
        );
    }

    @Override
    public SuccessResponse deleteCategory(String categoryKey) {
        Category persisted = findByCategoryId(categoryKey);
        categoryRepository.delete(persisted);
        getLogger().info("Successfully deleted category {}", persisted.getPublicKey());
        return new SuccessResponse(
                String.format(SUCCESSFULLY_DELETE_OPERATION, persisted.getPublicKey()),
                StatusOperation.SUCCESS
        );
    }
}

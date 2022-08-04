/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.repositories;

import com.journal.florist.backend.feature.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByPublicKey(String publicKey);

    Category findByNameCategory(String name);

    @Query("select (count(c) > 0) " +
            "from Category c " +
            "where upper(c.nameCategory) like upper(?1)")
    boolean existsByNameCategory(String categoryName);
}

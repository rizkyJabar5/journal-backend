/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.repositories;

import com.journal.florist.backend.feature.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductName(String productName);

    @Query("select p from Product p " +
            "where upper(p.productName) like upper(:productName) " +
            "or upper(p.category.publicKey) like upper (:categoryKey) " +
            "or upper(p.publicKey) like upper (:productKey) " +
            "order by p.createdAt desc")
    Page<Product> findByField(Pageable pageable);

    Page<Product> findAll(Pageable pageable);
    Optional<Product> findByPublicKey(String publicKey);

    @Query("select (count(p) > 0 ) " +
            "from Product p " +
            "where upper(p.productName) like upper(?1) ")
    boolean existsByProductName(String productName);

}
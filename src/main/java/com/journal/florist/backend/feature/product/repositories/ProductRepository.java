/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.product.repositories;

import com.journal.florist.backend.feature.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p " +
            "where upper(p.productName) like upper(concat('%', :productName, '%'))")
    List<Product> findByProductName(String productName);

    @Query("select p from Product p " +
            "where upper(p.productName) like upper(concat('%', :productName, '%')) " +
            "or upper(p.category.publicKey) like upper (:categoryId) " +
            "or upper(p.publicKey) like upper (:productId) " +
            "order by p.createdAt desc")
    Page<Product> findByField(@Param("productName") String productName,
                              @Param("categoryId") String categoryId,
                              @Param("productId") String productId,
                              Pageable pageable);

    @Query("select p from Product p " +
            "order by p.productName asc")
    Page<Product> findAllProduct(Pageable pageable);
    Optional<Product> findByPublicKey(String publicKey);

    @Query("select (count(p) > 0 ) " +
            "from Product p " +
            "where upper(p.productName) like upper(?1) ")
    boolean existsByProductName(String productName);

}
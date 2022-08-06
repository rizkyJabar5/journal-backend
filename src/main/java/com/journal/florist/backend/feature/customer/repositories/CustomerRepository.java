package com.journal.florist.backend.feature.customer.repositories;

import com.journal.florist.backend.feature.customer.model.Customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {

    Optional<Customers> findByPublicKey(String publicKey);

    @Query("select c from Customers c where upper(c.name) like upper(concat('%', ?1, '%'))")
    Page<Customers> findByNameIgnoreCaseContaining(String name, Pageable pageable);

    @Query("select c from Customers c")
    Page<Customers> findAllCustomers(Pageable pageable);

    @Query("select c from Customers c where upper(c.company.companyName) like upper(concat('%', ?1, '%'))")
    Customers findByCompanyIgnoreCaseContaining(String companyName);
}

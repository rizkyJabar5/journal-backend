/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.repositories;

import com.journal.florist.backend.feature.user.model.AppUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUsers, Long> {

    @Query("select a from AppUsers a " +
            "where upper(a.email) like upper(?1) " +
            "or upper(a.username) like upper(?2)")
    AppUsers findByUsernameOrEmail(String email, String username);

    Page<AppUsers> findBy(Pageable pageable);

    @Query("select u from AppUsers u " +
            "where upper(u.email) like upper(:email) " +
            "or upper(u.fullName) like upper(:fullName) " +
            "or upper(u.roles) like upper(:roles)")
    Page<AppUsers> findByField(@Param("email") String emailLike,
                               @Param("fullName") String fullName,
                               @Param("roles") String roleLike,
                               Pageable page);

    @Query("select u from AppUsers u " +
            "where upper(u.email) like upper(:email) " +
            "or upper(u.fullName) like upper(:fullName) " +
            "or upper(u.roles) like upper(:roles)")
    long countByField(@Param("email") String emailLike,
                      @Param("fullName") String fullName,
                      @Param("roles") String roleLike);

    @Query("select (count(a) > 0) from AppUsers a where a.email = ?1 or a.username = ?2")
    boolean existsByUsernameOrEmail(String email, String username);

}
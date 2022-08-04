/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FilterableCrudService<T extends BaseEntity>{

    Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

    long countAnyMatching(Optional<String> filter);
}

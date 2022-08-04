/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "apps_sequence")
    @SequenceGenerator(name = "appsSequence",
            sequenceName = "apps_sequence",
            allocationSize = 3)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "The id of the entity is required")
    private String publicKey;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Date createdAt;

    @CreatedBy
    @Column(updatable = false, nullable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Override
    public int hashCode() {
        return Objects.hashCode(getPublicKey());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof BaseEntity that)) return false;
        if(!that.canEqual(this) ) return false;

        return Objects.equals(getPublicKey(), that.getPublicKey());
    }
    /**
     * This method is meant for allowing to redefine equality on several levels of the class hierarchy
     * while keeping its contract.
     *
     * @see <a href="https://www.artima.com/articles/how-to-write-an-equality-method-in-java">More</a>
     * @param other is the other object use in equality test.
     * @return if the other object can be equal to this object.
     */
    protected boolean canEqual(Object other) {
        return other instanceof BaseEntity;
    }

    @PrePersist
    private void  onCreate() {
        if(Objects.isNull(getPublicKey())){
            setPublicKey(UUID.randomUUID().toString());
        }
    }
}

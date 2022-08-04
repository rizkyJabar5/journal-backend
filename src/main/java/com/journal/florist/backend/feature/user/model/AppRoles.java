/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.model;

import com.journal.florist.backend.feature.user.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    @SequenceGenerator(name = "userSequence",
            sequenceName = "user_sequence",
            initialValue = 3)
    private Long role_id;

    @Enumerated(EnumType.STRING)
    @Column
    private ERole roleName;

    @Column(length = 100)
    private String roleDescription;

    @Override
    public int hashCode() {
        return Objects.hash(getRoleName());
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof AppRoles role) || !super.equals(o)) return false;
        return Objects.equals(getRoleName(), role.roleName);
    }

}

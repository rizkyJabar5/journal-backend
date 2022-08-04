/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
public class AppUsers implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long userId;

    @NotBlank
    @Column(name = "fullname", length = 150)
    private String fullName;

    @Column(unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Size(min = 6, message = "Password length should be at least 6 characters")
    @Column(name = "hashed_password")
    private String hashedPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_fk_id")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "role_fk_id"))
    )
    private Collection<AppRoles> roles;

    @Column(name = "avatar")
    private String profileAvatar;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate = new Date(System.currentTimeMillis());

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addRole(final AppUsers user, final AppRoles role) {

    }

    @PrePersist
    @PreUpdate
    private void prepareData() {
        this.email = email == null ? null : email.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUsers appUser = (AppUsers) o;
        return userId != null && Objects.equals(userId, appUser.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


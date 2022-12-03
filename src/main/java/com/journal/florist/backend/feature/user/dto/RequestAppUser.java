package com.journal.florist.backend.feature.user.dto;

import com.journal.florist.backend.feature.user.enums.ERole;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.journal.florist.backend.feature.user.model.AppUsers} entity
 */
public record RequestAppUser(
        @Schema(example = "John Matador")
        @NotBlank(message = "Full name is required")
        String fullName,
        @Schema(example = "john68541")
        @NotBlank(message = "Username is required")
        String username,
        @Schema(example = "john1254@email.com")
        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is required")
        String email,
        @Size(min = 6, message = "Password length should be at least 6 characters")
        String hashedPassword,
        @NotBlank(message = "Roles is required, selected one or more")
        Set<ERole> rolesName,
        @RequestParam(required = false)
        MultipartFile profilePicture
) implements Serializable {

}
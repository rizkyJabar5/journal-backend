package com.journal.florist.backend.feature.user.dto;

import com.journal.florist.backend.feature.user.enums.ERole;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.journal.florist.backend.feature.user.model.AppUsers} entity
 */
public record RequestUpdateAppUser(
        Long userId,
        @Schema(example = "John Matador")
        @NotBlank(message = "Full name is required")
        String fullName,
        @NotBlank(message = "Roles is required, selected one or more")
        Set<ERole> rolesName,
        @RequestParam(required = false)
        MultipartFile profilePicture
) implements Serializable {

}
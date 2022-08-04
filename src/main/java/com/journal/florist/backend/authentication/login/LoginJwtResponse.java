package com.journal.florist.backend.authentication.login;

import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.app.utils.DateConverter;
import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.journal.florist.app.security.config.SecurityConst.TOKEN_PREFIX;

@Data
@Builder
public class LoginJwtResponse implements Serializable {

    private Long id;
    private String accessToken;
    private String fullName;
    private String username;
    private String email;
    private String type;
    private String joinDate;
    private String avatar;
    private List<String> roles;

    /**
     * Builds jwtResponseBuilder object from the specified userDetails.
     *
     * @param jwtToken the jwtToken
     * @return the jwtResponse
     */
    public static LoginJwtResponse buildJwtResponse(final String jwtToken) {
        return buildJwtResponse(jwtToken, null);
    }

    /**
     * Build jwtResponse object from the specified userDetails.
     *
     * @param jwToken     the jwToken.
     * @param userDetails the userDetails.
     * @return the jwtResponse object.
     */
    public static LoginJwtResponse buildJwtResponse(String jwToken,
                                                    AppUserBuilder userDetails) {

        AppUserBuilder localUserDetails = userDetails;

        if (Objects.isNull(localUserDetails)) {
            localUserDetails = SecurityUtils.getAuthenticatedUserDetails();
        }

        if (Objects.nonNull(localUserDetails)) {
            List<String> authorities = localUserDetails.getAuthorities().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()).toString()).toList();

            LocalDateTime dateTime = DateConverter.toLocalDate(localUserDetails.getJoinDate());
            String date = DateConverter.formatDate().format(dateTime);

            return LoginJwtResponse.builder()
                    .accessToken(jwToken)
                    .id(localUserDetails.getUserId())
                    .email(localUserDetails.getEmail())
                    .username(localUserDetails.getUsername())
                    .type(TOKEN_PREFIX)
                    .fullName(localUserDetails.getFullName())
                    .avatar(localUserDetails.getProfileAvatar())
                    .joinDate(date)
                    .roles(authorities)
                    .build();
        }
        return LoginJwtResponse.builder().build();
    }
}

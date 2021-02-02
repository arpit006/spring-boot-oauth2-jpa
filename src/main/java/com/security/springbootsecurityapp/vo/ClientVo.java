package com.security.springbootsecurityapp.vo;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientVo {

    private static final int ONE_WEEk_VALIDITY = 604800;
    private static final int ONE_DAY_VALIDITY = 93600;

    @NotNull
//    @Size(min = 5, max = 255)
    private final String clientId;

    private final String resourcedIds;

    @NotNull
//    @Size(min = 8, max = 255)
//    @Pattern(regexp = REGEX_ENFORCER, message = ERROR_MESSAGE_PASSWORD)
    private final String clientSecret;

    @NotNull
//    @Size(min = 4, max = 255)
    private final String scope;

    @NotNull
//    @SupportedGrantTypes
//    @Size(min = 5, max = 255)
    private final String authorizedGrantTypes;

    private final String webServerRedirectUri;

    @NotNull
//    @Size(min = 5, max = 255)
//    @SupportedAuthorities
    private final String authorities;

    @NotNull
//    @Min(ONE_DAY_VALIDITY)
    private final int accessTokenValidity;

    @NotNull
//    @Min(ONE_WEEk_VALIDITY)
    private final int refreshTokenValidity;

    private final String additionalInformation;

    private final String autoApproveScopes;
}

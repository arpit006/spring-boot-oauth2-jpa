package com.security.springbootsecurityapp.token;

import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Data
public class CustomAccessToken {

    private String accessToken;

    private OAuth2RefreshToken refreshToken;

    private String tokenType;

    private Date expiration;

    private Set<String> scope;

    public static CustomAccessToken of(OAuth2AccessToken token) {
        CustomAccessToken accessToken = new CustomAccessToken();
        accessToken.setAccessToken(token.getValue());
        accessToken.setRefreshToken(token.getRefreshToken());
        accessToken.setExpiration(token.getExpiration());
        accessToken.setTokenType(token.getTokenType());
        accessToken.setScope(token.getScope().stream().filter(Objects::nonNull).filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.toSet()));
        return accessToken;
    }
}

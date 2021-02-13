package com.security.springbootsecurityapp.config;

import com.security.springbootsecurityapp.principal.CustomAuthPrincipal;
import com.security.springbootsecurityapp.service.CustomUserDetailsService;
import com.security.springbootsecurityapp.util.OAuth2Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Component
@Slf4j
public class CustomAuthProvider  {

    @Value("${oauth.server}")
    private String server;

    @Value("${oauth.url}")
    private String url;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public OAuth2AccessToken authenticate(Authentication authentication, String grantType) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        OAuth2AccessToken token = null;
        try {
            token = obtainToken(username, password, grantType);
        } catch (Exception e) {
            log.error("Error obtaining token. Error is " + e.getMessage());
            throw new RuntimeException("Error obtaining token. Error is " + e.getMessage());
        }
        log.info("Access token obtained successfully::, " + token);
        log.info("SecurityContext:: " + SecurityContextHolder.getContext().getAuthentication());
        CustomAuthPrincipal userDetails = (CustomAuthPrincipal) customUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return token;
    }

    public OAuth2AccessToken obtainToken(String username, String password, String grantType) {
        ResourceOwnerPasswordResourceDetails resourceDetails = getResourceOwnerDetails(username, password, grantType);
        DefaultAccessTokenRequest defaultAccessTokenRequest = new DefaultAccessTokenRequest();
        OAuth2AccessToken token;
        try {
            token = new ResourceOwnerPasswordAccessTokenProvider().obtainAccessToken(resourceDetails, defaultAccessTokenRequest);
        } catch (OAuth2AccessDeniedException accessDeniedException) {
            throw new BadCredentialsException("Invalid credentials", accessDeniedException);
        }
        return token;
    }

    private ResourceOwnerPasswordResourceDetails getResourceOwnerDetails(String username, String password, String grantType) {
        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
        details.setAccessTokenUri(server + url);
        details.setClientId(OAuth2Util.getClientId());
        details.setClientSecret(OAuth2Util.getClientSecret());
        details.setUsername(username);
        details.setPassword(password);
        details.setGrantType(grantType);
        log.info("DETAILS: : " + details);
        return details;
    }
}

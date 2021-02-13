package com.security.springbootsecurityapp.config;

import com.security.springbootsecurityapp.principal.CustomAuthPrincipal;
import com.security.springbootsecurityapp.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * This class will make call to oauth2 to get token based on grant type
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Component
@Slf4j
public class CustomOAuth2AuthenticationProvider {

    @Value("${oauth.server}")
    private String server;

    @Value("${oauth.url}")
    private String url;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        try {
            log.info("Login request reached Auth provider");
            if (authentication != null) {
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();
                log.info("Login request sending to Oauth2 for getting token");
//                OAuth2RestTemplate oAuth2RestTemplate = ClientOauthRestUtil.get();
//                OAuth2AccessToken accessToken = oAuth2RestTemplate.getAccessToken();

//                log.info("Access token received from OAUTH2 successfully ::::: " + accessToken);
//                log.info("Access token received from OAUTH2 successfully ::::: " + response.getBody());
                // after getting access token load user from db and return the auth object
                Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
                OAuth2AccessToken auth2AccessToken = obtainToken(username, password);
                Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
                log.info("Access token obtained successfully:: " + auth2AccessToken);

                log.info("Auth before:: " + authentication2);
                log.info("Auth after:: " + authentication1);

//                String client = "my-trusted-ui:password";
//                client = new String(Base64.encodeBase64(client.getBytes()));
//                HttpHeaders httpHeaders = new HttpHeaders();
//                httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//                httpHeaders.add("Authorization", "Basic " + client);
//
//                HttpEntity<String> request = new HttpEntity<>(httpHeaders);

                log.info("==== " + SecurityContextHolder.getContext().getAuthentication().toString());
                CustomAuthPrincipal customAuthPrincipal = (CustomAuthPrincipal) customUserDetailsService.loadUserByUsername(authentication.getName());
                log.info("Principal object for logged in user is:: " + customAuthPrincipal);

                return new UsernamePasswordAuthenticationToken(customAuthPrincipal, customAuthPrincipal.getPassword(), customAuthPrincipal.getAuthorities());
            }
//        } catch (Exception e) {
//            log.error("Error occurred in retrieving token from Oauth2::: Error is " + e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
        return null;
    }

    public OAuth2AccessToken obtainToken(String username, String password) {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setAccessTokenUri("http://localhost:8082/oauth/token");
        resourceDetails.setClientId("my-trusted-ui");
        resourceDetails.setClientSecret("password");
        resourceDetails.setGrantType("password");
        resourceDetails.setUsername("arpit");
        resourceDetails.setPassword("password");
        DefaultAccessTokenRequest defaultAccessTokenRequest = new DefaultAccessTokenRequest();

        OAuth2AccessToken token;
        try {
            ResourceOwnerPasswordAccessTokenProvider resourceOwnerPasswordAccessTokenProvider = new ResourceOwnerPasswordAccessTokenProvider();
            token = resourceOwnerPasswordAccessTokenProvider.obtainAccessToken(resourceDetails, defaultAccessTokenRequest);

        } catch (OAuth2AccessDeniedException accessDeniedException) {
            throw new BadCredentialsException("Invalid credentials", accessDeniedException);
        }

        return token;

    }
}

package com.security.springbootsecurityapp.util;

import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
public class OAuth2Util {

    private static ThreadLocal<String> clientID = new ThreadLocal<>();

    private static ThreadLocal<String> clientSecret = new ThreadLocal<>();

    public static void setupClientData(String id, String secret) {
        clientID.set(id);
        clientSecret.set(secret);
    }

    public static void setClientID(String id) {
        clientID.set(id);
    }

    public static String getClientId() {
        return clientID.get();
    }

    public static void setClientSecret(String secret) {
        clientSecret.set(secret);
    }

    public static String getClientSecret() {
        return clientSecret.get();
    }

}

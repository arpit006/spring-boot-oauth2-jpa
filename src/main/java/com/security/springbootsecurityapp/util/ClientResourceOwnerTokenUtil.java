//package com.security.springbootsecurityapp.util;
//
//import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
//import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
//import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
//
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
// */
//public class ClientResourceOwnerTokenUtil {
//
//    public static ThreadLocal<ResourceOwnerPasswordResourceDetails> templates = new ThreadLocal<>();
//
//    private static ConcurrentHashMap<String, ResourceOwnerPasswordResourceDetails> clientRestTemplate = new ConcurrentHashMap<>();
//
//    public static ResourceOwnerPasswordResourceDetails getDetails(String clientID) {
//        return clientRestTemplate.getOrDefault(clientID, null);
//    }
//
//    public static void set(String clientID, ResourceOwnerPasswordResourceDetails resourceOwnerPasswordAccessTokenProvider) {
//        if (!clientRestTemplate.containsKey(clientID)) {
//            clientRestTemplate.put(clientID, resourceOwnerPasswordAccessTokenProvider);
//        }
//        templates.set(resourceOwnerPasswordAccessTokenProvider);
//    }
//
//    public static ResourceOwnerPasswordResourceDetails get() {
//        return templates.get();
//    }
//
//    public static void setupResourceOwnerPasswordProvider(String username, String password, String grantType, String url) {
//        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
//        //todo: get this later from redis
//        resourceDetails.setAccessTokenUri(url);
//        resourceDetails.setClientId(Oauth2ContextUtil.getClientID());
//        resourceDetails.setClientSecret(Oauth2ContextUtil.getClientSecret());
//        resourceDetails.setGrantType(grantType);
//        resourceDetails.setUsername(username);
//        resourceDetails.setPassword(password);
//        set(Oauth2ContextUtil.getClientID(), resourceDetails);
//    }
//
//}

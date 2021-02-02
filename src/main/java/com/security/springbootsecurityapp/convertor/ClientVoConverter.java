package com.security.springbootsecurityapp.convertor;

import com.security.springbootsecurityapp.vo.ClientVo;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
public class ClientVoConverter implements Converter<ClientVo, BaseClientDetails> {

//    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static final String ADDITIONAL_INFORMATION_KEY = "additional: ";

    @Override
    public BaseClientDetails convert(final MappingContext<ClientVo, BaseClientDetails> context) {
        final ClientVo client = context.getSource();
        final BaseClientDetails baseClientDetails = new BaseClientDetails(client.getClientId(),
                client.getResourcedIds(),
                client.getScope(),
                client.getAuthorizedGrantTypes(),
                client.getAuthorities(),
                client.getWebServerRedirectUri()
        );
//        baseClientDetails.setAuthorities(Arrays.stream(StringUtils.commaDelimitedListToStringArray(client.getAuthorities())).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        baseClientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
        baseClientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
        baseClientDetails.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        baseClientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(client.getAutoApproveScopes()));
        baseClientDetails.setAdditionalInformation(extractAdditionalInformation(client));
        return baseClientDetails;
    }

    private Map<String, String> extractAdditionalInformation(final ClientVo client) {
        final Map<String, String> additionalInfo = new HashMap<>();
        additionalInfo.put(ADDITIONAL_INFORMATION_KEY, client.getAdditionalInformation());
        return additionalInfo;
    }


}

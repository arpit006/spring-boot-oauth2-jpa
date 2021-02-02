package com.security.springbootsecurityapp.service;

import com.security.springbootsecurityapp.vo.ClientVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Service
public class ClientService implements IClientService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @Override
    public void save(ClientVo clientVo) {
        BaseClientDetails baseClientDetails = modelMapper.map(clientVo, BaseClientDetails.class);
        //todo: encode clientSecret
        clientRegistrationService.addClientDetails(baseClientDetails);
    }
}

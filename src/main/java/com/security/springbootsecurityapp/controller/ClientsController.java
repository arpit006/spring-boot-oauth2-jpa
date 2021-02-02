package com.security.springbootsecurityapp.controller;

import com.security.springbootsecurityapp.service.IClientService;
import com.security.springbootsecurityapp.vo.ClientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@RestController
@RequestMapping(value = "/clients")
public class ClientsController {

    @Autowired
    private IClientService clientService;

    @PostMapping(value = "")
    public String client(@RequestBody ClientVo clientVo) {
        clientService.save(clientVo);
        return "Client " + clientVo.getClientId() + " created Successfully";
    }
}

package com.PI.Back.PIBackend.controllers;

import com.PI.Back.PIBackend.services.impl.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);


    private final AdminService adminService;







}

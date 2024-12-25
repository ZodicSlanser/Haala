package com.gizasystems.userservice.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource {

    @GetMapping("/test")
    public String test() {

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

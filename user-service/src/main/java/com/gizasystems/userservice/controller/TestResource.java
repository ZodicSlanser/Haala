package com.gizasystems.userservice.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")

public class TestResource {

    @GetMapping("/test")
    public String test() {

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

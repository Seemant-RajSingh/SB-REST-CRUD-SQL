package com.srs.SpringSecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String greet(HttpServletRequest request) {
        return "welcome to home " + request.getSession().getId() + " " + request.getAttribute("_csrf");
    }
    // request.getAttribute("_csrf") is null when stateless

    @GetMapping("csrf-tkn")
    public CsrfToken gerCsrfToken(HttpServletRequest request) {
//        (CsrfToken) - type casting object(returned by getAttribute) to CsrfToken
        return (CsrfToken) request.getAttribute("_csrf");
    }

}

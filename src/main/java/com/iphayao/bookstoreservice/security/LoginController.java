package com.iphayao.bookstoreservice.security;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public void loginEndpoint() {
        // Nothing to response
    }
}

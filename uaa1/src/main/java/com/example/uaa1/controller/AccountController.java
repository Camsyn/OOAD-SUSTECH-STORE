package com.example.uaa1.controller;




import org.springframework.web.bind.annotation.*;



import java.security.Principal;


@RestController
@RequestMapping("/account")
public class AccountController {


    @GetMapping("/get")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}

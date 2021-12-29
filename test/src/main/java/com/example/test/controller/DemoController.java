package com.example.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@RestController
public class DemoController {

    @PostMapping("/hello")
    public String hello(){
        return "hello";
    }

}

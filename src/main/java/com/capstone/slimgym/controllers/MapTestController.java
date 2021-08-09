package com.capstone.slimgym.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class MapTestController {
    @GetMapping("/maptest")
    public String maptestpage(){
        return "maptest";
    }
}
